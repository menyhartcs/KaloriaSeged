import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import {isNullOrUndef} from "chart.js/helpers";

Chart.register(ArcElement, Tooltip, Legend);

const ProteinChart = ({ user, consumedProtein }) => {
    const defaultProtein = 120
    const maxProtein = isNullOrUndef(user.protein) ? defaultProtein : user.protein;
    const chartDataDescription = `${consumedProtein}/${maxProtein} (g)`
    consumedProtein = Math.min(consumedProtein, maxProtein);
    const data = {
        labels: ['Fehérje', 'Hátralévő'],
        datasets: [
            {
                label: 'Fehérje bevitel',
                data: [consumedProtein, maxProtein-consumedProtein],
                backgroundColor: ['#00bfff', '#f0f0f0'],
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

export default ProteinChart;