import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';

Chart.register(ArcElement, Tooltip, Legend);

const ProteinChart = ({ protein }) => {
    const data = {
        labels: ['Fehérje', 'Hátralévő'],
        datasets: [
            {
                label: 'Fehérje bevitel',
                data: [protein, 100-protein], // 19 g fehérje bevitel, 81 g hátravan a 100 g cél eléréséhez
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
                <p style={{ margin: 0, fontSize: 10 }}>19/100 (g)</p>
            </div>
        </div>
    );
};

export default ProteinChart;