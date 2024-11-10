import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';

Chart.register(ArcElement, Tooltip, Legend);

const ProteinChart = ({ user, consumedProtein }) => {
    const maxProtein = user.protein;
    const chartDataDescription = `${consumedProtein}/${maxProtein} (g)`
    consumedProtein = Math.min(consumedProtein, maxProtein);
    const data = {
        labels: ['Fehérje', 'Hátralévő'],
        datasets: [
            {
                label: 'Fehérje bevitel',
                data: [consumedProtein, maxProtein-consumedProtein], // {protein} g fehérje bevitel, {100-protein} g hátravan a 100 g cél eléréséhez
                backgroundColor: ['#00bfff', '#f0f0f0'],
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
        <div style={{ position: 'relative', width: 50, height: 50 }}>
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