import React, { useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
export default function LoginPage() {
 const [username, setUsername] = useState("admin");
 const [password, setPassword] = useState("Admin123!");
 const [error, setError] = useState(null);
 const auth = useAuth();
 const navigate = useNavigate();
 async function onSubmit(e) {
 e.preventDefault();
 setError(null);
 try {
 const res = await apiRequest("/api/v1/auth/login", {
 method: "POST",
 body: { username, password },
 });
 auth.login(res);
 navigate("/pages");
 } catch (e) {
 if (e instanceof ApiError) setError(typeof e.body === "string" ? e.body : "Login failed");
 else setError("Login failed");
 }
 }
 return (
 <div style={{ padding: 24 }}>
 <h2>Mycellius — Login</h2>
 <form onSubmit={onSubmit} style={{ display: "grid", gap: 12, maxWidth: 320 }}>
 <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="username" />
 <input value={password} onChange={(e) => setPassword(e.target.value)} placeholder="password"
type="password" />
 <button type="submit">Se connecter</button>
 </form>
 {error && <p style={{ marginTop: 12 }}>{String(error)}</p>}
 </div>
 );
}