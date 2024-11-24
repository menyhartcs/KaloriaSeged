import React, {useEffect, useState} from 'react'
import {deleteFood, listFoods} from "../service/FoodService.js";
import {useNavigate} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";
import {analyze} from "../service/AnalyzerService.js";

const ListFoodComponent = () => {

    const [foods, setFoods] = useState([])
    const [filteredFoods, setFilteredFoods] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const navigator = useNavigate();
    const [showCard, setShowCard] = useState(false);
    const [analysisResult, setAnalysisResult] = useState("");

    useEffect(() => {
        if (isNullOrUndef(localStorage.getItem("email")) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        } else {
            getAllFoods();
        }
    }, []);


    function getAllFoods() {
        listFoods().then((response) => {
            setFoods(response.data);
            setFilteredFoods(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function searchFoods(query) {
        setSearchQuery(query);
        if (query === "") {
            setFilteredFoods(foods);
        } else {
            const filtered = foods.filter(food =>
                food.name.toLowerCase().includes(query.toLowerCase())
            );
            setFilteredFoods(filtered);
        }
    }

    function addNewFood() {
        navigator("/add-food")
    }

    function updateFood(id) {
        navigator(`/edit-food/${id}`)
    }

    function eatFood(id) {
        navigator(`/add-userFoodLog/${id}`)
    }

    function removeFood(id) {
        console.log(id);

        deleteFood(id).then(() => {
            getAllFoods();
        }).catch(error => {
            console.error(error);
        })
    }

    function analyzeUserFoodLog(prompt) {
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

    function hideInfo() {
        setShowCard(!showCard)
    }

    return (
        <div className="container main-content">
            <h2 className="text-center">Elérhető ételek listája</h2>
            <div className="row">
                <div className="col-md-8">
                    <div className="card p-3">
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Keresés étel neve alapján"
                            value={searchQuery}
                            onChange={(e) => searchFoods(e.target.value)}
                        />

                        <button className="btn btn-dark mb-2" onClick={addNewFood}>Étel hozzáadása</button>
                        <table className="table table-striped table-bordered">
                            <tbody>
                            {
                                filteredFoods.map(food =>
                                    <tr key={food.id}>
                                        <td>
                                            <div className="form-group m-1">
                                                <h4><b>{food.name}</b></h4>
                                            </div>
                                        </td>
                                        <td>
                                            <ul>
                                                <li><b className="nutritionText energy">Energia:</b> {food.calorie} kcal</li>
                                                <li><b className="nutritionText protein">Fehérje:</b> {food.protein} g</li>
                                                <li><b className="nutritionText carbohydrate">Szénhidrát:</b> {food.carbohydrate} g</li>
                                                <li><b className="nutritionText fat">Zsír:</b> {food.fat} g</li>
                                            </ul>
                                        </td>
                                        <td>
                                            <button className="btn btn-success m-1"
                                                    onClick={() => eatFood(food.id)}>Megeszem
                                            </button>
                                            <button className="btn btn-warning m-1"
                                                    onClick={() => updateFood(food.id)}>Szerkeszt
                                            </button>
                                            <button className="btn btn-danger m-1"
                                                    onClick={() => removeFood(food.id)}>Töröl
                                            </button>
                                            <button className="btn btn-info m-1"
                                                    onClick={() => analyzeUserFoodLog(
                                                        `Röviden mutasd be az ételt: ${food.name}
                                                         és adj tanácsot, mikor lenne érdemes fogyasztani, röviden`
                                                    )}>Infó
                                            </button>
                                        </td>
                                    </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
                {showCard && (
                    <div className="col-md-4">
                        <div className="card p-3">
                            <div>
                                <h5>További információ</h5>
                                <p>{analysisResult}</p>
                            </div>
                            <button className="btn btn-info m-1"
                                    onClick={() => analyzeUserFoodLog()}>Elrejtés
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}

export default ListFoodComponent
