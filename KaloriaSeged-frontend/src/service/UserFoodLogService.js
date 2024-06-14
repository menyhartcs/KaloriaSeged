import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/userFoodLog';

export const listUserFoodLogs = () => axios.get(REST_API_BASE_URL);
export const createUserFoodLog = (userFoodLog) => axios.post(REST_API_BASE_URL, userFoodLogId);
export const getUserFoodLog = (userFoodLogId) => axios.get(REST_API_BASE_URL + "/" + userFoodLogId);
export const updateUserFoodLog = (userFoodLogId, userFoodLog) => axios.put(REST_API_BASE_URL + "/" + userFoodLogId, userFoodLog);
export const deleteUserFoodLog = (userFoodLogId) => axios.delete(REST_API_BASE_URL + "/" + userFoodLogId);