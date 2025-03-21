import { User } from "@/types/user";

// Use direct endpoint without /api prefix since we're using the proxy
const API_URL = '/users';

export const fetchUsers = async (): Promise<User[]> => {
  try {
    console.log("Fetching users from:", API_URL);
    const response = await fetch(API_URL);
    
    if (!response.ok) {
      console.error("Error response:", response.status, response.statusText);
      throw new Error(`Failed to fetch users: ${response.status} ${response.statusText}`);
    }
    
    const data = await response.json();
    console.log("Received data:", data);
    
    if (!data.data) {
      console.error("Unexpected response format:", data);
      return [];
    }
    
    return data.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const fetchUserById = async (id: number): Promise<User> => {
  try {
    console.log(`Fetching user with ID ${id} from: ${API_URL}/${id}`);
    const response = await fetch(`${API_URL}/${id}`);
    
    if (!response.ok) {
      console.error("Error response:", response.status, response.statusText);
      throw new Error(`Failed to fetch user with ID ${id}: ${response.status} ${response.statusText}`);
    }
    
    const data = await response.json();
    console.log("Received user data:", data);
    
    if (!data.data) {
      console.error("Unexpected response format:", data);
      throw new Error("Invalid response format");
    }
    
    return data.data;
  } catch (error) {
    console.error(`Error fetching user ${id}:`, error);
    throw error;
  }
};

export const createUser = async (user: Omit<User, 'id'>): Promise<User> => {
  try {
    console.log("Creating user:", user);
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    });
    
    console.log("Create user response status:", response.status);
    
    if (!response.ok) {
      const errorData = await response.json();
      console.error("Error creating user:", errorData);
      throw new Error(errorData.messages?.join(', ') || `Failed to create user: ${response.status} ${response.statusText}`);
    }
    
    const data = await response.json();
    console.log("Created user response:", data);
    
    if (!data.data) {
      console.error("Unexpected response format:", data);
      throw new Error("Invalid response format");
    }
    
    return data.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

export const updateUser = async (id: number, user: Partial<User>): Promise<User> => {
  try {
    console.log(`Updating user ${id}:`, user);
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    });
    
    console.log("Update user response status:", response.status);
    
    if (!response.ok) {
      const errorData = await response.json();
      console.error("Error updating user:", errorData);
      throw new Error(errorData.messages?.join(', ') || `Failed to update user ${id}: ${response.status} ${response.statusText}`);
    }
    
    const data = await response.json();
    console.log("Updated user response:", data);
    
    if (!data.data) {
      console.error("Unexpected response format:", data);
      throw new Error("Invalid response format");
    }
    
    return data.data;
  } catch (error) {
    console.error(`Error updating user ${id}:`, error);
    throw error;
  }
};

export const deleteUser = async (id: number): Promise<void> => {
  try {
    console.log(`Deleting user ${id}`);
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE',
    });
    
    console.log("Delete user response status:", response.status);
    
    if (!response.ok) {
      console.error("Error response:", response.status, response.statusText);
      throw new Error(`Failed to delete user ${id}: ${response.status} ${response.statusText}`);
    }
    
    console.log(`User ${id} deleted successfully`);
  } catch (error) {
    console.error(`Error deleting user ${id}:`, error);
    throw error;
  }
};