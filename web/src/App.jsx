import LoginPage from "./pages/LoginPage";
import PagesListPage from "./pages/PagesListPage";
import PageDetailPage from "./pages/PageDetailPage";
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "./auth/AuthContext";
import ForbiddenPage from "./pages/ForbiddenPage";

function RequireAuth({ children }) {
  const { token } = useAuth();
  if (!token) return <Navigate to="/login" replace />;
  return children;
}

// Redirection intelligente selon auth
function HomeRedirect() {
  const { token } = useAuth();
  return <Navigate to={token ? "/pages" : "/login"} replace />;
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/forbidden" element={<ForbiddenPage />} />

          <Route
            path="/pages"
            element={
              <RequireAuth>
                <PagesListPage />
              </RequireAuth>
            }
          />

          <Route
            path="/pages/:id"
            element={
              <RequireAuth>
                <PageDetailPage />
              </RequireAuth>
            }
          />

          {/* ✅ au lieu de tout envoyer vers /pages */}
          <Route path="/" element={<HomeRedirect />} />
          <Route path="*" element={<HomeRedirect />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
