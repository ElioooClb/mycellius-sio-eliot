import React, { useState } from "react";
import { View, Text, TextInput, Button } from "react-native";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
export default function NewPageScreen({ navigation }) {
 const { token, role, logout } = useAuth();
 const [id, setId] = useState("PAGE-001");
 const [title, setTitle] = useState("");
 const [content, setContent] = useState("");
 const [tagsText, setTagsText] = useState("tag1,tag2");
 const [error, setError] = useState(null);
 const [loading, setLoading] = useState(false);
 async function onCreate() {
 setError(null);
 setLoading(true);
 const tags = tagsText
 .split(",")
 .map(s => s.trim())
 .filter(Boolean)
 .map(name => ({ name })); // IMPORTANT: API attend TagRequest.name
 try {
 await apiRequest("/api/v1/pages", {
 method: "POST",
 token,
 body: { id, title, content, tags }
 });
 navigation.goBack(); // retour liste
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { await logout(); return; }
 setError(e instanceof ApiError ? JSON.stringify(e.body) : "Erreur création");
 } finally {
 setLoading(false);
 }
 }
 if (!(role === "DEV" || role === "ADMIN")) {
 return (
 <View style={{ padding: 16 }}>
 <Text>Accès interdit (DEV/ADMIN)</Text>
 </View>
 );
 }
 return (
 <View style={{ padding: 16, gap: 10 }}>
 <Text>ID (format PAGE-001)</Text>
 <TextInput value={id} onChangeText={setId} style={{ borderWidth: 1, padding: 8 }} />
 <Text>Titre</Text>
 <TextInput value={title} onChangeText={setTitle} style={{ borderWidth: 1, padding:
8 }} />
 <Text>Contenu</Text>
 <TextInput
 value={content}
 onChangeText={setContent}
 multiline
 style={{ borderWidth: 1, padding: 8, minHeight: 120 }}
 />
 <Text>Tags (virgules)</Text>
 <TextInput value={tagsText} onChangeText={setTagsText} style={{ borderWidth: 1,
padding: 8 }} />
 <Button title={loading ? "Création..." : "Créer"} onPress={onCreate} disabled={loading}
/>
 {error && <Text style={{ marginTop: 8 }}>{error}</Text>}
 </View>
 );
}