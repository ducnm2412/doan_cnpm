import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import LoginForm from "../pages/Login";
import RegisterForm from "../pages/Register";

const AppRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
    </Routes>
  );
};

export default AppRouter;
