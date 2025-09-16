import axios from "axios";
import API_BASE_URL from "./config";

const api = axios.create({
  baseURL: API_BASE_URL,
});

export const getTasks = () => api.get("/tasks");
export const createTask = (task) => api.post("/tasks", task);
export const updateTask = (id, task) => api.put(`/tasks/${id}`, task);
export const deleteTask = (id) => api.delete(`/tasks/${id}`);
