import React, {useEffect, useState} from 'react'
import {deleteExercise, listExercises} from "../service/ExerciseService.js";
import {useNavigate} from "react-router-dom";
import {isNullOrUndef} from "chart.js/helpers";
import {analyze} from "../service/AnalyzerService.js";

const ListExerciseComponent = () => {

    const [exercises, setExercises] = useState([])
    const [filteredExercises, setFilteredExercises] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const navigator = useNavigate();
    const [showCard, setShowCard] = useState(false);
    const [analysisResult, setAnalysisResult] = useState("");
    const currentEmail = localStorage.getItem("email");

    useEffect(() => {
        if (isNullOrUndef(currentEmail) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        } else {
            getAllExercises();
        }
    }, []);


    function getAllExercises() {
        listExercises().then((response) => {
            setExercises(response.data);
            setFilteredExercises(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function searchExercises(query) {
        setSearchQuery(query);
        if (query === "") {
            setFilteredExercises(exercises);
        } else {
            const filtered = exercises.filter(exercise =>
                exercise.name.toLowerCase().includes(query.toLowerCase())
            );
            setFilteredExercises(filtered);
        }
    }

    function addNewExercise() {
        navigator("/add-exercise")
    }

    function updateExercise(id) {
        navigator(`/edit-exercise/${id}`)
    }

    function doExercise(id) {
        navigator(`/add-userExerciseLog/${id}`)
    }

    function removeExercise(id) {
        console.log(id);

        deleteExercise(id).then(() => {
            getAllExercises();
        }).catch(error => {
            console.error(error);
        })
    }

    function analyzeUserExerciseLog(prompt) {
        setShowCard(!showCard)
        if (showCard === true) {
            setAnalysisResult("");
        }
        setAnalysisResult("Elemzés folyamatban....");
        analyze(prompt).then(response => {
            setAnalysisResult(response.data);
        })
            .catch(error => {
                console.error(error);
                setAnalysisResult("Error analyzing data");
            });
    }

    function adviceForMoving() {
        setShowCard(!showCard)
        if (showCard === true) {
            setAnalysisResult("");
        }

        let prompt = `Adj tanácsot, hogy mit mozogjak ma, az alábbi lista áll rendelkezésedre: ${getSimpleExerciseList()}
        ebben megtalálod a mozgásformák nevét, válassz ki belőle párat, amit mára ajánlanál`

        setAnalysisResult("Elemzés folyamatban....");
        analyze(prompt).then(response => {
            setAnalysisResult(response.data);
        })
            .catch(error => {
                console.error(error);
                setAnalysisResult("Error analyzing data");
            });
    }

    function getSimpleExerciseList() {
        return exercises.map(exercise => exercise.name);
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
                                onClick={() => adviceForMoving()}>Mit mozogjak ma?
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

    function showAdminButtons(exerciseId) {
        if ("admin@mail.com" === currentEmail) {
            return (
                <>
                    <button className="btn btn-warning m-1"
                            onClick={() => updateExercise(exerciseId)}><i className="bi bi-pencil"></i>
                    </button>
                    <button className="btn btn-danger m-1"
                            onClick={() => removeExercise(exerciseId)}><i className="bi bi-trash"></i>
                    </button>
                </>
            )
        }
    }

    function showUserButtons(exercise) {
        if ("admin@mail.com" !== currentEmail) {
            return (
                <>
                    <button className="btn btn-success m-1"
                            title="Megeszem"
                            onClick={() => doExercise(exercise.id)}><i className="bi bi-journal-text"></i>
                    </button>
                    <button className="btn btn-info m-1"
                            title="Információ"
                            onClick={() => analyzeUserExerciseLog(
                                `Röviden mutasd be a mozgásformát: ${exercise.name}
                                        és adj tanácsot, mikor lenne érdemes végezni és 
                                        mennyit célszerű kezdő vagy haladóként, röviden`
                            )}><i className="bi bi-info-circle"></i>
                    </button>
                </>
            )
        }
    }

    return (
        <div className="container main-content">
            <h2 className="text-center">Elérhető tevékenységek listája</h2>
            <div className="row">
                <div className="col-md-8">
                    <div className="card p-3">
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Keresés tevékenység neve alapján"
                            value={searchQuery}
                            onChange={(e) => searchExercises(e.target.value)}
                        />

                        <button className="btn btn-dark mb-2" onClick={addNewExercise}>Tevékenység hozzáadása</button>
                        <table className="table table-striped table-bordered">
                            <tbody>
                            {
                                filteredExercises.map(exercise =>
                                    <tr key={exercise.id}>
                                        <td>
                                            <div className="form-group m-1">
                                                <h4><b>{exercise.name}</b></h4>
                                            </div>
                                        </td>
                                        <td>
                                            <ul>
                                                <b className="nutritionText energy">Energia:</b> {exercise.calorie} kcal
                                            </ul>
                                        </td>
                                        <td>
                                            {showUserButtons(exercise)}
                                            {showAdminButtons(exercise.id)}
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

export default ListExerciseComponent
