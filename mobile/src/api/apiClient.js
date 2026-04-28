import { API_URL } from "../config";
export class ApiError extends Error {
 constructor(status, body) {
 super(`API error ${status}`);
 this.status = status;
 this.body = body;
 }
}
export async function apiRequest(path, { method = "GET", token = null, body = null } = {}) {
 const headers = { "Content-Type": "application/json" };
 if (token) headers.Authorization = `Bearer ${token}`;
 const res = await fetch(`${API_URL}${path}`, {
 method,
 headers,
 body: body ? JSON.stringify(body) : null,
 });
 const text = await res.text();
 let data = null;
 try { data = text ? JSON.parse(text) : null; } catch { data = text; }
 if (!res.ok) throw new ApiError(res.status, data);
 return data;
}