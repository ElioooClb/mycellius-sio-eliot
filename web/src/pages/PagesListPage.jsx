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
   if (!token) return;

   async function load() {
     try {
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
  <div className="page-shell">
    <div className="topbar">
      <div className="brand">
        <span>Mycellius</span>
      </div>

      <div className="topbar-right">
        <span className="role">
          Rôle : <strong>{role}</strong>
        </span>

        <button
          className="logout"
          onClick={() => { logout(); navigate("/login"); }}
        >
          Se déconnecter
        </button>
      </div>
    </div>

    <div className="card">
      <h2>Pages</h2>
      <p className="subtitle">Liste des pages du wiki</p>

      <div className="actions">
        <button
          className="btn secondary"
          onClick={() => { logout(); navigate("/login"); }}
        >
          Se déconnecter
        </button>

        {(role === "DEV" || role === "ADMIN") && (
          <button
            className="btn primary"
            onClick={() => navigate("/pages/new")}
          >
            + Créer une page
          </button>
        )}
      </div>

      {error && <p className="error-message">{error}</p>}

      <div className="divider" />

      <div className="list">
        {pages.map(p => (
          <Link to={`/pages/${p.id}`} key={p.id} className="item">
            <div className="icon"></div>

            <div className="content">
              <h2>{p.title}</h2>
              <p>Page du wiki Mycellius</p>
            </div>

            <span className="arrow">›</span>
          </Link>
        ))}
      </div>
    </div>
  </div>
 );
}