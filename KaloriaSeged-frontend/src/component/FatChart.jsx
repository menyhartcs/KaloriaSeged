import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import {isNullOrUndef} from "chart.js/helpers";

Chart.register(ArcElement, Tooltip, Legend);

const FatChart = ({ user, consumedFat }) => {
    const defaultFat = 55
    const maxFat = isNullOrUndef(user.fat) ? defaultFat : user.fat;
    const chartDataDescription = `${consumedFat}/${maxFat} (g)`
    consumedFat = Math.min(consumedFat, maxFat);
    const data = {
        labels: ['Fehérje', 'Hátralévő'],
        datasets: [
            {
                label: 'Fehérje bevitel',
                data: [consumedFat, maxFat-consumedFat], // 19 g fehérje bevitel, 81 g hátravan a 100 g cél eléréséhez
                backgroundColor: ['#FF69B4', '#f0f0f0'],
                borderWidth: 0,
            },
        ],
    };

    const options = {
        cutout: '70%', // A gyűrű vastagságának beállítása
        plugins: {
            tooltip: {
                enabled: false
            },
            legend: {
                display: false // Legenda kikapcsolása
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

export default FatChart;