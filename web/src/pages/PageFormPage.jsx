import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { useNavigate, useParams } from "react-router-dom";
export default function PageFormPage({ mode }) {
 const { id } = useParams();
 const { token, role, logout } = useAuth();
 const navigate = useNavigate();
 const [form, setForm] = useState({ id: "", title: "", content: "", tags: [] });
 const [tagsText, setTagsText] = useState("");
 const [error, setError] = useState(null);
 useEffect(() => {
 if (!(role === "DEV" || role === "ADMIN")) navigate("/forbidden");
 }, [role, navigate]);
 useEffect(() => {
 async function load() {
 if (mode !== "edit") return;
 try {
 const res = await apiRequest(`/api/v1/pages/${id}`, { token });
 setForm({ ...res, tags: res.tags ?? [] });
 setTagsText((res.tags ?? []).map(t => t.name).join(", "));
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
 if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
 }
 }
 load();
 }, [mode, id, token, logout, navigate]);
 async function onSubmit(e) {
 e.preventDefault();
 setError(null);
 const tags = tagsText
  .split(",")
  .map(s => s.trim())
  .filter(Boolean)
 .map(name => ({ name }));
 const payload = { ...form, tags };
 try {
 if (mode === "create") {
 await apiRequest("/api/v1/pages", { method: "POST", token, body: payload });
 navigate("/pages");
 } else {
 await apiRequest(`/api/v1/pages/${id}`, { method: "PUT", token, body: payload });
 navigate(`/pages/${id}`);
 }
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
 if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
 setError(e instanceof ApiError ? JSON.stringify(e.body) : "Erreur enregistrement");
 }
 }
 return (
 <div style={{ padding: 24 }}>
 <h2>{mode === "create" ? "Créer" : "Éditer"} une page</h2>
 <form onSubmit={onSubmit} style={{ display: "grid", gap: 12, maxWidth: 520 }}>
 <input
 value={form.id}
 onChange={(e) => setForm({ ...form, id: e.target.value })}
 placeholder="id (ex: PAGE-123)"
 disabled={mode === "edit"}
 />
 <input
 value={form.title}
 onChange={(e) => setForm({ ...form, title: e.target.value })}
 placeholder="title"
 />
 <textarea
 value={form.content}
 onChange={(e) => setForm({ ...form, content: e.target.value })}
 placeholder="content"
 rows={8}
 />
 <input
 value={tagsText}
 onChange={(e) => setTagsText(e.target.value)}
 placeholder="tags (séparés par virgule)"
 />
 <button type="submit">Enregistrer</button>
 </form>
 {error && <p>{error}</p>}
 </div>
 );
}