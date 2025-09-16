import { useEffect, useState } from "react";
import { getTasks, createTask, updateTask, deleteTask } from "./api";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const res = await getTasks();
      setTasks(res.data);
    } catch (err) {
      console.error("Error fetching tasks:", err);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim() || !description.trim()) return;

    try {
      if (editingId) {
        await updateTask(editingId, { title, description });
      } else {
        await createTask({ title, description });
      }
      setTitle("");
      setDescription("");
      setEditingId(null);
      fetchTasks();
    } catch (err) {
      console.error("Error saving task:", err);
    }
  };

  const handleEdit = (task) => {
    setTitle(task.title);
    setDescription(task.description);
    setEditingId(task.id);
  };

  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      fetchTasks();
    } catch (err) {
      console.error("Error deleting task:", err);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-100 via-purple-100 to-pink-100 flex items-center justify-center p-6">
      <div className="w-full max-w-4xl bg-white shadow-2xl rounded-3xl p-10">
        {/* Header */}
        <h1 className="text-4xl font-extrabold text-center text-gray-800 mb-8 flex items-center justify-center gap-2">
          📋 Task Manager
        </h1>

        {/* Task Form (aligned row) */}
        <form onSubmit={handleSubmit} className="flex gap-3 items-center mb-10">
          <input
            type="text"
            placeholder="Task title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="flex-1 h-12 px-4 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
          />
          <input
            type="text"
            placeholder="Task description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="flex-1 h-12 px-4 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
          />
          <button
            type="submit"
            className="h-12 px-6 bg-indigo-500 text-white rounded-lg font-medium hover:bg-indigo-600 transition whitespace-nowrap"
          >
            {editingId ? "✏️ Update" : "➕ Add"}
          </button>
        </form>

        {/* Task List */}
        <div className="space-y-4">
          {tasks.length === 0 ? (
            <p className="text-gray-500 text-center italic">No tasks found</p>
          ) : (
            tasks.map((task) => (
              <div
                key={task.id}
                className="p-5 bg-gradient-to-r from-gray-50 to-gray-100 border border-gray-200 rounded-xl shadow-sm flex justify-between items-center hover:shadow-md transition"
              >
                {/* Task Info */}
                <div className="flex-1 pr-4">
                  <h3 className="font-bold text-gray-800 text-lg">{task.title}</h3>
                  <p className="text-gray-600 text-sm mt-1">{task.description}</p>
                </div>

                {/* Buttons */}
                <div className="flex space-x-2">
                  <button
                    onClick={() => handleEdit(task)}
                    className="flex items-center gap-1 bg-yellow-400 text-white px-4 py-2 rounded-lg hover:bg-yellow-500 transition"
                  >
                    ✏️ Edit
                  </button>
                  <button
                    onClick={() => handleDelete(task.id)}
                    className="flex items-center gap-1 bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition"
                  >
                    🗑️ Delete
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
