import * as d3 from "https://cdn.jsdelivr.net/npm/d3@7/+esm";

document.addEventListener("DOMContentLoaded", function() {
    const chartData2Element = document.getElementById('chartData2');
    if (chartData2Element) {
        const chartData2 = JSON.parse(chartData2Element.textContent);
        console.log("chartData2:", chartData2);

        if (chartData2.length > 0) {
            chartData2.forEach(d => {
                d.lastPredictionDate = new Date(d.lastPredictionDate);
                d.probability = +d.probability;
            });

            // Declare the chart dimensions and margins.
            const width = 640;
            const height = 250;
            const margin = { top: 20, right: 20, bottom: 30, left: 40 };

            // Declare the x (horizontal position) scale.
            const x = d3.scaleTime()
                .domain(d3.extent(chartData2, d => d.lastPredictionDate))
                .range([margin.left, width - margin.right]);

            // Declare the y (vertical position) scale.
            const y = d3.scaleLinear()
                .domain([0, 100])
                .range([height - margin.bottom, margin.top]);

            // Create the line generator.
            const line = d3.line()
                .x(d => x(d.lastPredictionDate))
                .y(d => y(d.probability));

            // Create the SVG container.
            const svg = d3.create("svg")
                .attr("width", width)
                .attr("height", height)
                .attr("viewBox", [0, 0, width, height])
                .attr("style", "max-width: 100%; height: auto; height: intrinsic;");

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

            // Color scale using d3.schemeTableau10.
            const color = d3.scaleOrdinal(d3.schemeTableau10);

            // Append the lines to the SVG.
                svg.append("path")
                    .datum(chartData2)
                    .attr("fill", "none")
                    .attr("stroke", color)
                    .attr("stroke-width", 1.5)
                    .attr("d", line);

                // Append circles at each data point for the category.
                svg.append("g")
                    .selectAll("circle")
                    .data(chartData2)
                    .join("circle")
                    .attr("cx", d => x(d.lastPredictionDate))
                    .attr("cy", d => y(d.probability))
                    .attr("r", 3)
                    .attr("fill", "black");

            const chart2 = document.getElementById('chart2');

            // Append the SVG element.
            chart2.appendChild(svg.node());
        } else {
            console.error("chartData2 is empty or not provided.");
        }
    } else {
        console.error("chartData2 element not found.");
    }
});