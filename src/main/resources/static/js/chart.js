import * as d3 from "https://cdn.jsdelivr.net/npm/d3@7/+esm";

document.addEventListener("DOMContentLoaded", function() {
    const chartDataElement = document.getElementById('chartData');
    if (chartDataElement) {
        const chartData = JSON.parse(chartDataElement.textContent);
        if (chartData.length > 0) {
            chartData.forEach(d => {
                d.lastPredictionDate = new Date(d.lastPredictionDate);
                d.probability = +d.probability;
            });

            // Declare the chart dimensions and margins.
            const width = 640;
            const height = 250;
            const margin = { top: 20, right: 20, bottom: 30, left: 40 };

            // Declare the x (horizontal position) scale.
            const x = d3.scaleTime()
                .domain(d3.extent(chartData, d => d.lastPredictionDate))
                .range([margin.left, width - margin.right]);

            // Declare the y (vertical position) scale.
            const y = d3.scaleLinear([0, d3.max(chartData, d => d.probability)], [height - margin.bottom, margin.top]);


               //Declare the area generator.
//               const area = d3.area()
//                   .x(d => x(d.lastPredictionDate))
//                   .y0(y(0))
//                   .y1(d => y(d.probability));

            // Group data by securityName.
            const dataGroupedBySecurity = d3.group(chartData, d => d.securityName);

            // Create the line generator.
            const line = d3.line()
                .x(d => x(d.lastPredictionDate))
                .y(d => y(d.probability));

            // Create the SVG container.
            const svg = d3.create("svg")
                .attr("width", width)
                .attr("height", height)
                .attr("viewBox", [0, 0, width, height])


              // Append a path for the area (under the axes).
//                            svg.append("path")
//                                .attr("fill", "forestgreen")
//                                .attr("d", line(chartData));

            // Append x-axis with dynamic time formatting.
            svg.append("g")
                .attr("transform", `translate(0,${height - margin.bottom})`)
                .call(d3.axisBottom(x).ticks(width / 80).tickSizeOuter(0)
                );

            // Add the y-axis.
            svg.append("g")
                .attr("transform", `translate(${margin.left},0)`)
                .call(d3.axisLeft(y).ticks(height / 40))
                .call(g => g.select(".domain").remove())
                .call(g => g.selectAll(".tick line").clone()
                    .attr("x2", width - margin.left - margin.right)
                    .attr("stroke-opacity", 0.1))
                .call(g => g.append("text")
                    .attr("x", -margin.left)
                    .attr("y", 10)
                    .attr("fill", "currentColor")
                    .attr("text-anchor", "start")
                    .text("↑ Probability (%)"));




//            // Color scale using d3.schemeTableau10.
            const color = d3.scaleOrdinal(d3.schemeTableau10);
//
//            // Append the lines to the SVG.
//            dataGroupedBySecurity.forEach((values, key) => {
//                const lineColor = color(key);
//
                svg.append("path")
                    .attr("fill", "none")
                    .attr("stroke", "steelblue")
                    .attr("stroke-width", 1.5)
                    .attr("d", line(chartData));
                    //.attr("d", area);

//            });

            const chart = document.getElementById('chart');

            // Append the SVG element.
            chart.appendChild(svg.node());
        } else {
            console.error("chartData is empty or not provided.");
        }
    } else {
        console.error("chartData element not found.");
    }
});
