// Extract dates and values from product_prices
var dates = product_prices.dates;
var values = product_prices.values;

console.log('Product:', product);     //debug
console.log('Product Prices:', product_prices);       //debug

// Create a line chart using Chart.js
var ctx = document.getElementById('priceChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: dates,
        datasets: [{
            label: 'Price Over Time',
            data: values,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
            fill: false
        }]
    },
    options: {
        scales: {
            x: {
                type: 'category', // Use 'category' for non-time x-axis (strings)
                labels: dates,     // Provide labels directly
                offset: true, // Add some space before and after the labels
            },
            y: {
                beginAtZero: false,
                grace: '50%', // Add space above the highest value
            }
        }
    }
});