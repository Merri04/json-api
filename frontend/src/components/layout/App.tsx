import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../Login-Logout/Login";
import LogOut from "../Login-Logout/LogOut";
import QRCodeGenerator from "../../pages/Home/home";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/logout" element={<LogOut />} />
                <Route path="/home" element={<QRCodeGenerator />} />
            </Routes>
        </Router>
    );
}

export default App;
