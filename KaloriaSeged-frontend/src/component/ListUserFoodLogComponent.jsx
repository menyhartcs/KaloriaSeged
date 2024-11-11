import React, {useEffect, useState} from 'react'
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
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
import {isNullOrUndef} from "chart.js/helpers";
import {getUserByEmail} from "../service/UserService.js";
import {analyze} from "../service/AnalyzerService.js";

const ListUserFoodLogComponent = () => {

    const [userFoodLogs, setUserFoodLogs] = useState([])
    const [consumedNutrients, setConsumedNutrients] = useState({
        calorie: 0, protein: 0, carbohydrate: 0, fat: 0
    });
    const [selectedDate, setSelectedDate] = useState(getCurrentDate);
    const [analysisResult, setAnalysisResult] = useState("");
    const [user, setUser] = useState([])
    const navigator = useNavigate();

    useEffect(() => {
        const userEmail = localStorage.getItem("email");
        getUserByEmail(userEmail).then((response) => {
            setUser(response.data);
        });
    }, []);

    const userId = user.id;
    const date = getCurrentDate();

    useEffect(() => {
        console.log("userId=" + userId);
        console.log("date=" + date);

        if (isNullOrUndef(userId)) {
            getUserFoodLogsByDate(selectedDate);
        } else {
            getUserFoodLogsByUserIdAndDate(userId, date);
        }

    }, [userId, selectedDate]);

    useEffect(() => {
        if (userFoodLogs.length > 0) {
            const summedNutrients = userFoodLogs.reduce((dictionary, log) => {
                const food = log.food || {};
                return {
                    calorie: dictionary.calorie + (food.calorie || 0),
                    protein: dictionary.protein + (food.protein || 0),
                    carbohydrate: dictionary.carbohydrate + (food.carbohydrate || 0),
                    fat: dictionary.fat + (food.fat || 0)
                };
            }, { calorie: 0, protein: 0, carbohydrate: 0, fat: 0 });

            setConsumedNutrients(summedNutrients);
            console.log("Bevitt tápanyagok:", summedNutrients);
        }
    }, [userFoodLogs]);

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
        analyze(prompt).then(response => {
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
            if (isNullOrUndef(userId)) {
                getUserFoodLogsByDate(selectedDate);
            } else {
                getUserFoodLogsByUserIdAndDate(userId, date);
            }
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
            if (isNullOrUndef(userId)) {
                getUserFoodLogsByDate(selectedDate);
            } else {
                getUserFoodLogsByUserIdAndDate(userId, selectedDate);
            }
        }
    };

    return (
        <div className="container">
            <h2 className="text-center">Étkezési napló</h2>
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
            <div className="charts-container">
                <FatChart user={user} consumedFat={consumedNutrients.fat}/>
                <CarbohydrateChart user={user} consumedCarbohydrate={consumedNutrients.carbohydrate}/>
                <ProteinChart user={user} consumedProtein={consumedNutrients.protein}/>
                <CalorieChart user={user} consumedCalorie={consumedNutrients.calorie}/>
            </div>
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
                                    <li>Fehérje: {userFoodLog.food.protein}g</li>
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
                            <td>
                                {/* Eredmény kiírása */}
                                {analysisResult && (
                                    <div className="analysis-result">
                                        <h3>Analysis Result:</h3>
                                        <p>{analysisResult}</p>
                                    </div>
                                )}
                            </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListUserFoodLogComponent
