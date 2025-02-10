import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../Login-Logout/Login";
import LogOut from "../Login-Logout/LogOut";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/logout" element={<LogOut />} />
            </Routes>
        </Router>
    );
}

export default App;
