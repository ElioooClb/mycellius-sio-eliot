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
  <div className="login-container">
    <div className="login-card">
      <h2>Mycellius — Login</h2>

      <form onSubmit={onSubmit}>
        <input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Identifiant"
        />

        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Mot de passe"
        />

        {error && <p className="error-message">{error}</p>}

        <button type="submit">Se connecter</button>
      </form>
    </div>
  </div>
 );
}