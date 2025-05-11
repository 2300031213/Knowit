import React, { createContext, useContext, useState, useEffect } from 'react';

// Create auth context
const AuthContext = createContext(null);

// Define user types
const ROLES = {
  STUDENT: 'student',
  INSTRUCTOR: 'instructor'
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    // Check if user is stored in localStorage
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      // In a real app, this would be an API call
      // This is a mock implementation for demo purposes
      
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 800));
      
      // Hardcoded users for demo
      const users = [
        { id: '1', name: 'Student User', email: 'student@example.com', password: 'password', role: ROLES.STUDENT },
        { id: '2', name: 'Instructor User', email: 'instructor@example.com', password: 'password', role: ROLES.INSTRUCTOR }
      ];
      
      const foundUser = users.find(u => u.email === email && u.password === password);
      
      if (!foundUser) {
        throw new Error('Invalid email or password');
      }
      
      // Remove password before storing
      const { password: _, ...userWithoutPassword } = foundUser;
      
      // Store in localStorage and state
      localStorage.setItem('user', JSON.stringify(userWithoutPassword));
      setUser(userWithoutPassword);
      
      return userWithoutPassword;
    } catch (error) {
      throw error;
    }
  };

  const register = async (name, email, password, role) => {
    try {
      // In a real app, this would be an API call
      // Mock implementation for demo
      
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 800));
      
      // Check if email is already in use
      if (email === 'student@example.com' || email === 'instructor@example.com') {
        throw new Error('Email already in use');
      }
      
      // Create a new user
      const newUser = {
        id: Date.now().toString(),
        name,
        email,
        role: role || ROLES.STUDENT
      };
      
      // Store in localStorage and state
      localStorage.setItem('user', JSON.stringify(newUser));
      setUser(newUser);
      
      return newUser;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('user');
    setUser(null);
  };

  const value = {
    user,
    login,
    register,
    logout,
    isAuthenticated: !!user,
    isInstructor: user?.role === ROLES.INSTRUCTOR,
    isStudent: user?.role === ROLES.STUDENT,
    loading,
    ROLES
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;