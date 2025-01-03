import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import {isNullOrUndef} from "chart.js/helpers";

Chart.register(ArcElement, Tooltip, Legend);

const CarbohydrateChart = ({ user, consumedCarbohydrate }) => {
    const defaultCarbohydrate = 250
    const maxCarbohydrate = isNullOrUndef(user.carbohydrate) ? defaultCarbohydrate : user.carbohydrate;
    const chartDataDescription = `${consumedCarbohydrate}/${maxCarbohydrate} (g)`
    consumedCarbohydrate = Math.min(consumedCarbohydrate, maxCarbohydrate);
    const data = {
        labels: ['Fehérje', 'Hátralévő'],
        datasets: [
            {
                label: 'Fehérje bevitel',
                data: [consumedCarbohydrate, maxCarbohydrate-consumedCarbohydrate],
                backgroundColor: ['#FFA500', '#f0f0f0'],
                borderWidth: 0,
            },
        ],
    };

    const options = {
        cutout: '70%',
        plugins: {
            tooltip: {
                enabled: false
            },
            legend: {
                display: false
            },
        },
    };

    return (
        <div style={{ position: 'relative', width: 70, height: 70 }}>
            <Doughnut data={data} options={options} />
            <div style={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                textAlign: 'center',
                pointerEvents: 'none',
            }}>
                <p style={{ margin: 0, fontSize: 10 }}>{chartDataDescription}</p>
            </div>
        </div>
    );
};

export default CarbohydrateChart;