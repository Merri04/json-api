import axios from "axios";
import { useState } from "react";
import styled from "styled-components";

// Set the base URL for all Axios requests
axios.defaults.baseURL = "http://localhost:8080"; // Replace with your backend URL
axios.defaults.withCredentials = true; // Include cookies for session management

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  background-color: white;
`;

const FormWrapper = styled.div`
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
`;

const Title = styled.h1`
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
`;

const Label = styled.label`
  font-size: 14px;
  margin-bottom: 8px;
  display: block;
`;

const Input = styled.input`
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
`;

const Button = styled.button`
  width: 100%;
  padding: 12px;
  background-color: blue;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;

  &:hover {
    background-color: darkblue;
  }
`;

const ErrorMessage = styled.div`
  color: red;
  text-align: center;
  margin-bottom: 20px;
`;

const Login: React.FC = () => {
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    });
    const [error, setError] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axios.post("/login", new URLSearchParams(credentials).toString(), {
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
            });
            window.location.href = "/"; // Redirect after successful login
        } catch (err) {
            setError("Invalid credentials");
        }
    };

    return (
        <Container>
            <FormWrapper>
                <Title>Please sign in</Title>
                {error && <ErrorMessage>{error}</ErrorMessage>}
                <form onSubmit={handleSubmit}>
                    <div>
                        <Label htmlFor="username">Fødselsnummer</Label>
                        <Input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="Fødselsnummer"
                            value={credentials.username}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div>
                        <Label htmlFor="password">Password</Label>
                        <Input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Password"
                            value={credentials.password}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <Button type="submit">Sign in</Button>
                </form>
            </FormWrapper>
        </Container>
    );
};

export default Login;
