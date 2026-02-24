import React, { useEffect, useState } from "react";
import { View, Text, Button, ScrollView } from "react-native";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
export default function PageDetailScreen({ route, navigation }) {
 const { id } = route.params;
 const { token, logout } = useAuth();
 const [page, setPage] = useState(null);
 useEffect(() => {
 (async () => {
 try {
 const res = await apiRequest(`/api/v1/pages/${id}`, { token });
 setPage(res);
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { await logout(); return; }
 }
 })();
 }, [id, token]);
 return (
 <ScrollView style={{ padding: 16 }}>
 {!page ? (
 <Text>Chargement...</Text>
 ) : (
 <>
 <Text style={{ fontSize: 20, fontWeight: "700" }}>{page.title}</Text>
 <Text style={{ marginTop: 8 }}>ID : {page.id}</Text>
 <Text style={{ marginTop: 16, lineHeight: 20 }}>{page.content}</Text>
 </>
 )}
 <View style={{ marginTop: 20 }}>
 <Button title="Retour" onPress={() => navigation.goBack()} />
 </View>
 </ScrollView>
 );
}