import React, { useEffect, useState } from "react";
import { View, Text, TextInput, Button } from "react-native";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
export default function EditPageScreen({ route, navigation }) {
 const { id } = route.params;
 const { token, role, logout } = useAuth();
 const [title, setTitle] = useState("");
 const [content, setContent] = useState("");
 const [tagsText, setTagsText] = useState("");
 const [error, setError] = useState(null);
 const [loading, setLoading] = useState(false);
 useEffect(() => {
 (async () => {
 try {
 const page = await apiRequest(`/api/v1/pages/${id}`, { token });
 setTitle(page.title ?? "");
 setContent(page.content ?? "");
 setTagsText((page.tags ?? []).join(", ")); // API renvoie List<String>
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { await logout(); return; }
 setError("Erreur chargement");
 }
 })();
 }, [id, token]);
 async function onSave() {
 setError(null);
 setLoading(true);
 const tags = tagsText
 .split(",")
 .map(s => s.trim())
 .filter(Boolean)
 .map(name => ({ name })); // API attend TagRequest.name
 try {
 await apiRequest(`/api/v1/pages/${id}`, {
 method: "PUT",
 token,
 body: { id, title, content, tags }
 });
 navigation.goBack(); // retour détail
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { await logout(); return; }
 setError(e instanceof ApiError ? JSON.stringify(e.body) : "Erreur update");
 } finally {
 setLoading(false);
 }
 }
 if (!(role === "DEV" || role === "ADMIN")) {
 return <View style={{ padding: 16 }}><Text>Accès interdit (DEV/ADMIN)</Text></View>;
 }
 return (
 <View style={{ padding: 16, gap: 10 }}>
 <Text>ID : {id}</Text>
 <Text>Titre</Text>
 <TextInput value={title} onChangeText={setTitle} style={{ borderWidth: 1, padding: 8 }} />
 <Text>Contenu</Text>
 <TextInput
 value={content}
 onChangeText={setContent}
 multiline
 style={{ borderWidth: 1, padding: 8, minHeight: 120 }}
 />
 <Text>Tags (virgules)</Text>
 <TextInput value={tagsText} onChangeText={setTagsText} style={{ borderWidth: 1, padding:
8 }} />
 <Button title={loading ? "Enregistrement..." : "Enregistrer"} onPress={onSave}
disabled={loading} />
 {error && <Text>{error}</Text>}
 </View>
 );
}