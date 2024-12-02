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
    const currentEmail = localStorage.getItem("email");

    useEffect(() => {
        if (isNullOrUndef(currentEmail) && isNullOrUndef(localStorage.getItem("token"))) {
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

    function adviceForEating() {
        setShowCard(!showCard)
        if (showCard === true) {
            setAnalysisResult("");
        }

        let prompt = `Adj tanácsot, hogy mit egyek ma, az alábbi lista áll rendelkezésedre: ${getSimpleFoodList()}
        ebben megtalálod az ételek nevét és a hozzájuk tartozó makrotápanyagokat, 
        válassz ki belőle hármat, 
        amit mára ajánlanál`

        setAnalysisResult("Elemzés folyamatban....");
        analyze(prompt).then(response => {
            setAnalysisResult(response.data);
        })
            .catch(error => {
                console.error(error);
                setAnalysisResult("Error analyzing data");
            });
    }

    function getSimpleFoodList() {
        return foods.map(food => food.name);
    }

    function hideInfo() {
        setShowCard(!showCard)
    }

    function showInfoPanel() {
        if ("admin@mail.com" === currentEmail) { return; }
        return (
            <>
                <div className="col-md-4">
                    <div className="card p-3">
                        <button className="btn btn-info m-1"
                                onClick={() => adviceForEating()}>Mit egyek ma?
                        </button>
                        {showCard && (
                            <div>
                                <h5>További információ</h5>
                                <p>{analysisResult}</p>
                                <button className="btn btn-info m-1"
                                        onClick={() => hideInfo()}>Elrejtés
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </>
        )
    }

    function showAdminButtons(foodId) {
        if ("admin@mail.com" === currentEmail) {
            return (
                <>
                    <button className="btn btn-warning m-1"
                            onClick={() => updateFood(foodId)}><i className="bi bi-pencil"></i>
                    </button>
                    <button className="btn btn-danger m-1"
                            onClick={() => removeFood(foodId)}><i className="bi bi-trash"></i>
                    </button>
                </>
            )
        }
    }

    function showUserButtons(food) {
        if ("admin@mail.com" !== currentEmail) {
            return (
                <>
                    <button className="btn btn-success m-1"
                            title="Megeszem"
                            onClick={() => eatFood(food.id)}><i className="bi bi-journal-text"></i>
                    </button>
                    <button className="btn btn-info m-1"
                            title="Információ"
                            onClick={() => analyzeUserFoodLog(
                                `Röviden mutasd be az ételt: ${food.name}
                                        és adj tanácsot, mikor lenne érdemes fogyasztani, röviden`
                            )}><i className="bi bi-info-circle"></i>
                    </button>
                </>
            )
        }
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
                                            <b className="nutritionText energy">Energia:</b> {food.calorie} kcal<br/>
                                            <b className="nutritionText protein">Fehérje:</b> {food.protein} g<br/>
                                            <b className="nutritionText carbohydrate">Szénhidrát:</b> {food.carbohydrate} g<br/>
                                            <b className="nutritionText fat">Zsír:</b> {food.fat} g
                                        </td>
                                        <td>
                                            {showUserButtons(food)}
                                            {showAdminButtons(food.id)}
                                        </td>
                                    </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
                {showInfoPanel()}
            </div>
        </div>
    )
}

export default ListFoodComponent
