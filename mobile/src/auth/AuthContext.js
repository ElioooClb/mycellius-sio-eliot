import React, { createContext, useContext, useEffect, useMemo, useState } from "react";
import * as SecureStore from "expo-secure-store";
const AuthContext = createContext(null);
const TOKEN_KEY = "mycellius_token";
const ROLE_KEY = "mycellius_role";
const USERNAME_KEY = "mycellius_username";
export function AuthProvider({ children }) {
 const [booting, setBooting] = useState(true);
 const [token, setToken] = useState(null);
 const [role, setRole] = useState(null);
 const [username, setUsername] = useState(null);
 // Onboarding : relire le token au démarrage
 useEffect(() => {
 (async () => {
 const t = await SecureStore.getItemAsync(TOKEN_KEY);
 const r = await SecureStore.getItemAsync(ROLE_KEY);
 const u = await SecureStore.getItemAsync(USERNAME_KEY);
 setToken(t);
 setRole(r);
 setUsername(u);
 setBooting(false);
 })();
 }, []);
 const value = useMemo(() => ({
 booting,
 token,
 role,
 username,
 login: async ({ token, role, username }) => {
 await SecureStore.setItemAsync(TOKEN_KEY, token);
 await SecureStore.setItemAsync(ROLE_KEY, role);
 await SecureStore.setItemAsync(USERNAME_KEY, username);
 setToken(token);
 setRole(role);
 setUsername(username);
 },
 logout: async () => {
 await SecureStore.deleteItemAsync(TOKEN_KEY);
 await SecureStore.deleteItemAsync(ROLE_KEY);
 await SecureStore.deleteItemAsync(USERNAME_KEY);
 setToken(null);
 setRole(null);
 setUsername(null);
 }
 }), [booting, token, role, username]);
 return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
export function useAuth() {
 return useContext(AuthContext);
}