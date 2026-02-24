import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate } from "react-router-dom";
export default function PagesListPage() {
 const { token, role, logout } = useAuth();
 const [pages, setPages] = useState([]);
 const [error, setError] = useState(null);
 const navigate = useNavigate();
 useEffect(() => {
   if (!token) return; // ✅ tant qu’on n’a pas de token, on ne fait aucun appel API

   async function load() {
     try {
       // Variante A (Spring Page<>): /api/v1/pages?page=0&size=10
       // Variante B (liste simple): /api/v1/pages
       let res;
       try {
         res = await apiRequest("/api/v1/pages?page=0&size=10", { token });
       } catch {
         res = await apiRequest("/api/v1/pages", { token });
       }
       const content = Array.isArray(res) ? res : (res?.content ?? []);
       setPages(content);
     } catch (e) {
       if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
       if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
       setError("Erreur chargement pages");
     }
   }

   load();
 }, [token, logout, navigate]);
 return (
 <div style={{ padding: 24 }}>
 <h2>Pages</h2>
 <p>Rôle : {role}</p>
 <div style={{ marginBottom: 12 }}>
 {(role === "DEV" || role === "ADMIN") && <Link to="/pages/new">Créer une page</Link>}
 </div>
 {error && <p>{error}</p>}
 <ul>
 {pages.map(p => (
 <li key={p.id}>
 <Link to={`/pages/${p.id}`}>{p.title} ({p.id})</Link>
 </li>
 ))}
 </ul>
 </div>
 );
}