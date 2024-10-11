import React, {useEffect, useState} from 'react'
import {useNavigate, useParams} from "react-router-dom";
import {
    deleteUserFoodLog,
    listUserFoodLogs,
    listUserFoodLogsByDate,
    listUserFoodLogsByUserIdAndDate
} from "../service/UserFoodLogService.js";
import ProteinChart from "./ProteinChart.jsx";
import CarbohydrateChart from "./CarbohydrateChart.jsx";
import FatChart from "./FatChart.jsx";
import CalorieChart from "./CalorieChart.jsx";
import '../style/Charts.css';
import moment from 'moment';
import axios from "axios";

const ListUserFoodLogComponent = () => {

    const [userFoodLogs, setUserFoodLogs] = useState([])
    const [selectedDate, setSelectedDate] = useState(getCurrentDate);
    const [analysisResult, setAnalysisResult] = useState("");

    const {userId} = useParams();
    console.log("userId=" + userId)
    const navigator = useNavigate();

    useEffect(() => {
        if (userId === undefined) {
            getUserFoodLogsByDate(selectedDate);
        } else {
            getUserFoodLogsByUserIdAndDate(userId, selectedDate);
        }
    }, []);

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    function getAllUserFoodLogs() {
        listUserFoodLogs().then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getUserFoodLogsByDate(date) {
        listUserFoodLogsByDate(date).then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getUserFoodLogsByUserIdAndDate(userId, date) {
        listUserFoodLogsByUserIdAndDate(userId, date).then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewUserFoodLog() {
        navigator("/add-userFoodLog")
    }

    function updateUserFoodLog(id) {
        navigator(`/edit-userFoodLog/${id}`)
    }

    function analyzeUserFoodLog(prompt) {
        // HTTP kérés küldése az axios segítségével
        axios.post('http://localhost:8080/complete', { prompt })
            .then(response => {
                // Eredmény frissítése
                setAnalysisResult(response.data);
            })
            .catch(error => {
                console.error(error);
                setAnalysisResult("Error analyzing data");
            });
    }

    function removeUserFoodLog(id) {
        console.log(id);

        deleteUserFoodLog(id).then(() => {
            getAllUserFoodLogs();
        }).catch(error => {
            console.error(error);
        })
    }

    const handleChangeDate = (event) => {
        const newDate = event.target.value;
        setSelectedDate(newDate); // `selectedDate` állapot frissítése az új dátummal
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            if (userId === undefined) {
                getUserFoodLogsByDate(selectedDate);
            } else {
                getUserFoodLogsByUserIdAndDate(userId, selectedDate);
            }
        }
    };

    return (
        <div className="container">
            <h2 className="text-center">List of UserFoodLogs</h2>
            <div className="mb-3">
                <label htmlFor="dateInput" className="form-label">Select current date:</label>
                <input
                    type="date"
                    id="dateInput"
                    className="form-control"
                    value={selectedDate}
                    onChange={handleChangeDate}
                    onKeyDown={handleKeyDown} // Esemény figyelése az Enter lenyomására
                />
            </div>
            <button className="btn btn-dark mb-2" onClick={addNewUserFoodLog}>Add UserFoodLog</button>
            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>User</th>
                    <th>Food</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {
                    userFoodLogs.map(userFoodLog =>
                        <tr key={userFoodLog.id}>
                            <td>{userFoodLog.id}</td>
                            <td>
                                <ul>
                                    <li>{userFoodLog.user.id}</li>
                                    <li>{userFoodLog.user.name}</li>
                                    <li>{userFoodLog.user.email}</li>
                                </ul>
                            </td>
                            <td>
                                <ul>
                                    <li>{userFoodLog.food.id}</li>
                                    <li>{userFoodLog.food.name}</li>
                                    <li>Energia: {userFoodLog.food.calorie} kcal</li>
                                    <li>Zsír: {userFoodLog.food.fat}g</li>
                                    <li>Szénhidrát: {userFoodLog.food.carbohydrate}g</li>
                                    <li>Fehérje: {userFoodLog.food.protein}g
                                        <div className="charts-container">
                                            <FatChart fat={userFoodLog.food.fat}/>
                                            <CarbohydrateChart carbohydrate={userFoodLog.food.carbohydrate}/>
                                            <ProteinChart protein={userFoodLog.food.protein}/>
                                            <CalorieChart calorie={userFoodLog.food.calorie}/>
                                        </div>
                                    </li>
                                </ul>
                            </td>
                            <td>{userFoodLog.date}</td>
                            <td>
                                <button className="btn btn-info"
                                        onClick={() => updateUserFoodLog(userFoodLog.id)}>Update
                                </button>
                                <button className="btn btn-danger" onClick={() => removeUserFoodLog(userFoodLog.id)}
                                        style={{marginLeft: "10px"}}>Delete
                                </button>
                                <button className="btn btn-info"
                                        onClick={() => analyzeUserFoodLog(
                                            `Röviden elemezd az ételt:
                                             Energia: ${userFoodLog.food.calorie}
                                             Zsír: ${userFoodLog.food.fat}
                                             Szénhidrát: ${userFoodLog.food.carbohydrate}
                                             Fehérje: ${userFoodLog.food.protein}
                                             és adj tanácsot, mikor lenne érdemes fogyasztani, röviden`
                                        )}>Elemezd
                                </button>
                            </td>
                            {/* Eredmény kiírása */}
                            {analysisResult && (
                                <div className="analysis-result">
                                    <h3>Analysis Result:</h3>
                                    <p>{analysisResult}</p>
                                </div>
                            )}
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListUserFoodLogComponent
