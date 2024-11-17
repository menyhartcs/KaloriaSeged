import React, {useEffect, useState} from 'react'
import {useNavigate} from "react-router-dom";
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
    const [showCard, setShowCard] = useState(false);

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
                    calorie: dictionary.calorie + (Math.round(food.calorie * log.amount / 100) || 0),
                    protein: dictionary.protein + (Math.round(food.protein * log.amount / 100) || 0),
                    carbohydrate: dictionary.carbohydrate + (Math.round(food.carbohydrate * log.amount / 100) || 0),
                    fat: dictionary.fat + (Math.round(food.fat * log.amount / 100) || 0)
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
        navigator("/Foods")
    }

    function updateUserFoodLog(id) {
        navigator(`/edit-userFoodLog/${id}`)
    }

    function analyzeUserFoodLog(prompt) {
        console.log(userFoodLogs)
        setShowCard(!showCard)
        if (showCard === true) {
            setAnalysisResult("");
        }
        // HTTP kérés küldése az axios segítségével
        setAnalysisResult("Elemzés folyamatban....");
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
        <div className="container main-content m-6">
            <h2 className="text-center">Étkezési napló</h2>
            <div className="row">
                <div className="col-md-8">
                    <div className="card p-3">
                        <label htmlFor="dateInput" className="form-label">Jelenlegi dátum:</label>
                        <input
                            type="date"
                            id="dateInput"
                            className="form-control"
                            value={selectedDate}
                            onChange={handleChangeDate}
                            onKeyDown={handleKeyDown} // Esemény figyelése az Enter lenyomására
                        />
                        <button className="btn btn-dark mb-2" onClick={addNewUserFoodLog}>Étel fogyasztás</button>
                        <table className="table table-striped table-bordered mt-3">
                            {/*<thead>*/}
                            {/*<tr>*/}
                            {/*    <th>Id</th>*/}
                            {/*    <th>User</th>*/}
                            {/*    <th>Food</th>*/}
                            {/*    <th>Date</th>*/}
                            {/*</tr>*/}
                            {/*</thead>*/}
                            <tbody>
                            {
                                userFoodLogs.map(userFoodLog =>
                                    <tr key={userFoodLog.id}>
                                        <td>
                                            <div className="form-group m-1">
                                                <h4><b>{userFoodLog.food.name}</b></h4>
                                            </div>
                                        </td>
                                        <td>
                                            <ul>
                                                <li><b className="nutritionText energy">Energia:</b> {Math.round(userFoodLog.food.calorie * userFoodLog.amount / 100)} kcal</li>
                                                <li><b className="nutritionText protein">Fehérje:</b> {Math.round(userFoodLog.food.protein * userFoodLog.amount / 100)} g</li>
                                                <li><b className="nutritionText carbohydrate">Szénhidrát:</b> {Math.round(userFoodLog.food.carbohydrate * userFoodLog.amount / 100)} g</li>
                                                <li><b className="nutritionText fat">Zsír:</b> {Math.round(userFoodLog.food.fat * userFoodLog.amount / 100)} g</li>
                                            </ul>
                                        </td>
                                        <td>{userFoodLog.date}</td>
                                        <td>
                                            <button className="btn btn-success m-1"
                                                    onClick={() => updateUserFoodLog(userFoodLog.id)}>Szerkeszt</button>
                                            <button className="btn btn-danger m-1"
                                                    onClick={() => removeUserFoodLog(userFoodLog.id)}>Töröl</button>
                                            <button className="btn btn-info m-1"
                                                    onClick={() => analyzeUserFoodLog(
                                                        `Röviden elemezd az ételt:
                                                         Energia: ${Math.round(userFoodLog.food.calorie * userFoodLog.amount / 100)}
                                                         Zsír: ${Math.round(userFoodLog.food.fat * userFoodLog.amount / 100)}
                                                         Szénhidrát: ${Math.round(userFoodLog.food.carbohydrate * userFoodLog.amount / 100)}
                                                         Fehérje: ${Math.round(userFoodLog.food.protein * userFoodLog.amount / 100)}
                                                         és adj tanácsot, mikor lenne érdemes fogyasztani, röviden`
                                                    )}>Elemezd
                                            </button>
                                        </td>
                                    </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="col-md-4">
                    <div className="card p-3">
                        <div className="charts-container">
                            <ProteinChart user={user} consumedProtein={consumedNutrients.protein}/>
                            <CarbohydrateChart user={user} consumedCarbohydrate={consumedNutrients.carbohydrate}/>
                            <FatChart user={user} consumedFat={consumedNutrients.fat}/>
                            <CalorieChart user={user} consumedCalorie={consumedNutrients.calorie}/>
                        </div>
                        <button
                            className="btn btn-info mt-3"
                            onClick={() => analyzeUserFoodLog(
                                `Ez egy étkezési napló: ${userFoodLogs} röviden elemezd a 
                                napi beállított személyes cél:
                                Napi kitűzött energia bevitel: ${user.calorie}
                                Napi kitűzött fehérje bevitel: ${user.protein}
                                Napi kitűzött szénhidrát bevitel: ${user.carbohydrate}
                                Napi kitűzött zsír bevitel: ${user.fat}
                                és személyes adatok alapján:
                                Súly: ${user.weight}
                                Magasság: ${user.height}
                                Kor: ${user.age}
                                és adj tanácsot, mire lenne érdemes odafigyelni, röviden`
                            )}>
                            {showCard ? "Elrejtés" : "Elemzés"}
                        </button>
                        <h5>További információ</h5>
                        {/* A megjelenő Card */}
                        {showCard && (
                            <p>{analysisResult}</p>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ListUserFoodLogComponent
