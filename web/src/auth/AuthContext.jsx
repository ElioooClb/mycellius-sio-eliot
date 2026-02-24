import React, { createContext, useContext, useMemo, useState } from "react";
const AuthContext = createContext(null);
export function AuthProvider({ children }) {
 const [token, setToken] = useState(localStorage.getItem("token"));
 const [role, setRole] = useState(localStorage.getItem("role"));
 const [username, setUsername] = useState(localStorage.getItem("username"));
 const value = useMemo(() => ({
 token,
 role,
 username,
 login: ({ token, role, username }) => {
 localStorage.setItem("token", token);
 localStorage.setItem("role", role);
 localStorage.setItem("username", username);
 setToken(token);
 setRole(role);
 setUsername(username);
 },
 logout: () => {
 localStorage.removeItem("token");
 localStorage.removeItem("role");
 localStorage.removeItem("username");
 setToken(null);
 setRole(null);
 setUsername(null);
 }
 }), [token, role, username]);
 return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
export function useAuth() {
 return useContext(AuthContext);
}