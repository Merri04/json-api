import React from 'react';
import { useNavigate } from 'react-router-dom';

const LogOut: React.FC = () => {
    const navigate = useNavigate();

    const handleLogOut = () => {

        localStorage.removeItem('authToken');
        sessionStorage.removeItem('authToken');


        navigate('/login');
    };

    return (
        <div>
            <button onClick={handleLogOut}>Log Out</button>
        </div>
    );
};

export default LogOut;
