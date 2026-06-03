import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function PageDetailPage() {
  const { id } = useParams();
  const { token, role, logout } = useAuth();
  const [page, setPage] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    async function load() {
      try {
        const res = await apiRequest(
          `/api/v1/pages/${id}`,
          { token }
        );

        setPage(res);
      } catch (e) {
        if (e instanceof ApiError && e.status === 401) {
          logout();
          navigate("/login");
          return;
        }

        if (e instanceof ApiError && e.status === 403) {
          navigate("/forbidden");
          return;
        }

        if (e instanceof ApiError && e.status === 404) {
          setError("Page introuvable.");
          return;
        }

        setError("Erreur pendant le chargement de la page.");
      }
    }

    load();
  }, [id, token, logout, navigate]);

  if (error) {
    return (
      <div style={{ padding: 24 }}>
        <p>{error}</p>

        <Link to="/pages">
          Retour à la liste
        </Link>
      </div>
    );
  }

  if (!page) {
    return (
      <div style={{ padding: 24 }}>
        Chargement...
      </div>
    );
  }

  return (
    <div style={{ padding: 24 }}>
      <h2>{page.title}</h2>

      <p>
        <b>ID</b> : {page.id}
      </p>

      {page.confidential && (
        <p>
          <strong>Page confidentielle</strong>
        </p>
      )}

      <pre style={{ whiteSpace: "pre-wrap" }}>
        {page.content}
      </pre>

      {(role === "DEV" || role === "ADMIN") && (
        <p>
          <Link to={`/pages/${page.id}/edit`}>
            Éditer
          </Link>
        </p>
      )}

      <p>
        <Link to="/pages">
          Retour à la liste
        </Link>
      </p>
    </div>
  );
}