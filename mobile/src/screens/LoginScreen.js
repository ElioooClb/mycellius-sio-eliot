import React, { useState } from "react";
import { View, Text, TextInput, Button } from "react-native";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
export default function LoginScreen() {
 const [username, setUsername] = useState("admin");
 const [password, setPassword] = useState("Admin123!");
 const [error, setError] = useState(null);
 const auth = useAuth();
 async function onLogin() {
 setError(null);
 try {
 const res = await apiRequest("/api/v1/auth/login", {
 method: "POST",
 body: { username, password },
 });
 await auth.login(res);
 // La navigation bascule automatiquement (token présent)
 } catch (e) {
 if (e instanceof ApiError) setError(typeof e.body === "string" ? e.body : "Login failed");
 else setError("Login failed");
 }
 }
 return (
 <View style={{ padding: 16, gap: 12 }}>
 <Text>Username</Text>
 <TextInput value={username} onChangeText={setUsername} autoCapitalize="none"
 style={{ borderWidth: 1, padding: 8 }} />
 <Text>Password</Text>
 <TextInput value={password} onChangeText={setPassword} secureTextEntry
 style={{ borderWidth: 1, padding: 8 }} />
 <Button title="Se connecter" onPress={onLogin} />
 {error && <Text style={{ marginTop: 8 }}>{String(error)}</Text>}
 </View>
 );
}