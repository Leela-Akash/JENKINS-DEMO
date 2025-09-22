import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';
import config from './config.js';

const VolunteerManager = () => {
  const [events, setEvents] = useState([]);
  const [event, setEvent] = useState({
    id: '',
    eventName: '',
    description: '',
    date: '',
    hours: '',
    volunteerName: '',
    contact: ''
  });
  const [idToFetch, setIdToFetch] = useState('');
  const [fetchedEvent, setFetchedEvent] = useState(null);
  const [message, setMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  const baseUrl = config.url;

  useEffect(() => {
    fetchAllEvents();
  }, []);

  // ✅ Fetch all events
  const fetchAllEvents = async () => {
    try {
      const res = await axios.get(`${baseUrl}/all`);
      setEvents(res.data);
    } catch (error) {
      setMessage('❌ Failed to fetch volunteer events.');
    }
  };

  // ✅ Handle input changes
  const handleChange = (e) => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  // ✅ Validate form
  const validateForm = () => {
    const keysToCheck = editMode
      ? ['eventName', 'description', 'date', 'hours', 'volunteerName', 'contact']
      : ['eventName', 'description', 'date', 'hours', 'volunteerName', 'contact'];

    for (let key of keysToCheck) {
      if (!event[key] || event[key].toString().trim() === '') {
        setMessage(`⚠️ Please fill out the "${key}" field.`);
        return false;
      }
    }
    return true;
  };

  // ✅ Add new event (exclude id)
  const addEvent = async () => {
    if (!validateForm()) return;
    try {
      const { id, ...eventData } = event; // remove id
      await axios.post(`${baseUrl}/add`, eventData);
      setMessage('✅ Event added successfully.');
      fetchAllEvents();
      resetForm();
    } catch (error) {
      setMessage('❌ Error adding event.');
    }
  };

  // ✅ Update existing event
  const updateEvent = async () => {
    if (!validateForm()) return;
    try {
      await axios.put(`${baseUrl}/update`, event);
      setMessage('✅ Event updated successfully.');
      fetchAllEvents();
      resetForm();
    } catch (error) {
      setMessage('❌ Error updating event.');
    }
  };

  // ✅ Delete event
  const deleteEvent = async (id) => {
    try {
      const res = await axios.delete(`${baseUrl}/delete/${id}`);
      setMessage(res.data || '✅ Event deleted.');
      fetchAllEvents();
    } catch (error) {
      setMessage('❌ Error deleting event.');
    }
  };

  // ✅ Fetch event by ID
  const getEventById = async () => {
    if (!idToFetch) {
      setMessage('⚠️ Enter an ID to fetch.');
      return;
    }
    try {
      const res = await axios.get(`${baseUrl}/get/${idToFetch}`);
      setFetchedEvent(res.data);
      setMessage('');
    } catch (error) {
      setFetchedEvent(null);
      setMessage('❌ Event not found.');
    }
  };

  // ✅ Enable edit mode
  const handleEdit = (ev) => {
    setEvent(ev);
    setEditMode(true);
    setMessage(`✏️ Editing event with ID ${ev.id}`);
  };

  // ✅ Reset form
  const resetForm = () => {
    setEvent({
      id: '',
      eventName: '',
      description: '',
      date: '',
      hours: '',
      volunteerName: '',
      contact: ''
    });
    setEditMode(false);
  };

  return (
    <div className="student-container">
      {message && (
        <div className={`message-banner ${message.includes('❌') ? 'error' : 'success'}`}>
          {message}
        </div>
      )}

      <h2>Volunteer Hours Tracker</h2>

      {/* Add / Edit Form */}
      <div>
        <h3>{editMode ? 'Edit Event' : 'Add Event'}</h3>
        <div className="form-grid">
          {editMode && (
            <input
              type="number"
              name="id"
              placeholder="ID"
              value={event.id}
              onChange={handleChange}
              disabled // ✅ ID is read-only in edit mode
            />
          )}
          <input type="text" name="eventName" placeholder="Event Name" value={event.eventName} onChange={handleChange} />
          <input type="text" name="description" placeholder="Description" value={event.description} onChange={handleChange} />
          <input type="date" name="date" value={event.date} onChange={handleChange} />
          <input type="number" name="hours" placeholder="Hours" value={event.hours} onChange={handleChange} />
          <input type="text" name="volunteerName" placeholder="Volunteer Name" value={event.volunteerName} onChange={handleChange} />
          <input type="text" name="contact" placeholder="Contact" value={event.contact} onChange={handleChange} />
        </div>

        <div className="btn-group">
          {!editMode ? (
            <button className="btn-blue" onClick={addEvent}>Add Event</button>
          ) : (
            <>
              <button className="btn-green" onClick={updateEvent}>Update Event</button>
              <button className="btn-gray" onClick={resetForm}>Cancel</button>
            </>
          )}
        </div>
      </div>

      {/* Fetch by ID */}
      <div>
        <h3>Get Event By ID</h3>
        <input
          type="number"
          value={idToFetch}
          onChange={(e) => setIdToFetch(e.target.value)}
          placeholder="Enter ID"
        />
        <button className="btn-blue" onClick={getEventById}>Fetch</button>

        {fetchedEvent && (
          <div>
            <h4>Event Found:</h4>
            <pre>{JSON.stringify(fetchedEvent, null, 2)}</pre>
          </div>
        )}
      </div>

      {/* Show All Events */}
      <div>
        <h3>All Events</h3>
        {events.length === 0 ? (
          <p>No events found.</p>
        ) : (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  {Object.keys(event).map((key) => (
                    <th key={key}>{key}</th>
                  ))}
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {events.map((ev) => (
                  <tr key={ev.id}>
                    {Object.keys(event).map((key) => (
                      <td key={key}>{ev[key]}</td>
                    ))}
                    <td>
                      <div className="action-buttons">
                        <button className="btn-green" onClick={() => handleEdit(ev)}>Edit</button>
                        <button className="btn-red" onClick={() => deleteEvent(ev.id)}>Delete</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default VolunteerManager;
