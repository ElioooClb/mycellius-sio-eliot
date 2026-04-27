import React, { useEffect, useState } from "react";
import { View, Text, TextInput, Button, FlatList, TouchableOpacity } from "react-native";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
export default function PagesListScreen({ navigation }) {
 const { token, role, logout } = useAuth();
 const [pages, setPages] = useState([]);
 const [query, setQuery] = useState("");
 const [error, setError] = useState(null);
 async function loadList() {
 setError(null);
 try {
 let res;
 try {
 res = await apiRequest("/api/v1/pages?page=0&size=50", { token });
 } catch {
 res = await apiRequest("/api/v1/pages", { token });
 }
 const content = Array.isArray(res) ? res : (res?.content ?? []);
 setPages(content);
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { await logout(); return; }
 setError("Erreur chargement pages");
 }
 }
 async function onSearch() {
 setError(null);
 const q = query.trim();
 if (!q) { loadList(); return; }
 try {
 // Recherche “titre contient”
 // Si votre API expose /api/v1/pages/search?title=..., c’est parfait
 const res = await apiRequest(`/api/v1/pages/search?title=${encodeURIComponent(q)}`,
{ token });
 const content = Array.isArray(res) ? res : (res?.content ?? []);
 setPages(content);
 } catch (e) {
 // Si l’endpoint search n’existe pas encore, on retombe sur la liste locale filtrée
 // (Option de secours pédagogique : pas idéale métier, mais évite de bloquer la séance)
 try {
 await loadList();
 setPages(prev => prev.filter(p => (p.title ??
"").toLowerCase().includes(q.toLowerCase())));
 } catch {
 setError("Recherche impossible (endpoint absent + liste KO)");
 }
 }
 }
 useEffect(() => {
  const unsub = navigation.addListener("focus", loadList);
  return unsub;
 }, [navigation, token]);
 return (
 <View style={{ padding: 16, gap: 12 }}>
 <View style={{ flexDirection: "row", gap: 8 }}>
 <TextInput placeholder="Rechercher par titre..." value={query}
onChangeText={setQuery}
 style={{ borderWidth: 1, padding: 8, flex: 1 }} />
 <Button title="OK" onPress={onSearch} />
 </View>
 {(role === "DEV" || role === "ADMIN") && (
  <Button title="Créer une page" onPress={() => navigation.navigate("NewPage")} />
 )}
 <Button title="Rafraîchir" onPress={loadList} />
 {error && <Text>{error}</Text>}
 <FlatList
 data={pages}
 keyExtractor={(item) => String(item.id)}
 renderItem={({ item }) => (
 <TouchableOpacity onPress={() => navigation.navigate("PageDetail", { id: item.id })}>
 <Text style={{ paddingVertical: 10 }}>
 {item.title} ({item.id})
 </Text>
 </TouchableOpacity>
 )}
 />
 </View>
 );
}