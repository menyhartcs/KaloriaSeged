import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart, BarElement, CategoryScale, LinearScale, Tooltip, Legend } from 'chart.js';
import {isNullOrUndef} from "chart.js/helpers";

Chart.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

const CalorieChart = ({ user, consumedCalorie }) => {
    const defaultCalorie = 2000
    const maxCalorie = isNullOrUndef(user.calorie) ? defaultCalorie : user.calorie;
    const chartDataDescription = `${consumedCalorie}/${maxCalorie} kcal`
    consumedCalorie = Math.min(consumedCalorie, maxCalorie);
    const percentageFilled = (consumedCalorie / maxCalorie) * 100;

    const data = {
        labels: ['Kalória'],
        datasets: [
            {
                label: 'Kalória bevitel',
                data: [percentageFilled],
                backgroundColor: '#32CD32',
                borderWidth: 1,
                barThickness: 50,
            },
        ],
    };

    const options = {
        indexAxis: 'y', // Make the bar chart horizontal
        plugins: {
            tooltip: {
                enabled: true
            },
            legend: {
                display: false
            },
        },
        scales: {
            x: {
                display: false, // Remove the x-axis
                beginAtZero: true,
                max: 100,
            },
            y: {
                display: false, // Remove the y-axis
            }
        }
    };

    return (
        <div style={{ position: 'relative', width: '100px', height: '50px', backgroundColor: '#f0f0f0', border: '1px solid #ddd' }}>
            <Bar data={data} options={options} />
            <div style={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                textAlign: 'center',
                pointerEvents: 'none',
            }}>
                <p style={{ margin: 0, fontSize: 16 }}>{chartDataDescription}</p>
            </div>
        </div>
    );
};

export default CalorieChart;
