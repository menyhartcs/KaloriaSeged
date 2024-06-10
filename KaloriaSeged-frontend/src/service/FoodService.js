import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/food';

export const listFoods = () => axios.get(REST_API_BASE_URL);
export const createFood = (food) => axios.post(REST_API_BASE_URL, food);
export const getFood = (foodId) => axios.get(REST_API_BASE_URL + "/" + foodId);
export const updateFood = (foodId, food) => axios.put(REST_API_BASE_URL + "/" + foodId, food);
export const deleteFood = (foodId) => axios.delete(REST_API_BASE_URL + "/" + foodId);