import { line } from "d3"

let d3 = null
class Triangulations {
    constructor(parameters, lib_d3) {
        d3 = lib_d3
        this.n = parameters[0]
        this.width = parameters[1]
        this.height = parameters[2]

        this.svg = this.createSVG()

        this.startX = this.svg._groups[0][0].clientWidth / 2 - this.width / 2
        this.startY = this.svg._groups[0][0].clientHeight / 2 - this.height / 2

        this.nodes = []
        this.arcDots = [];
        this.lines = [];
        this.circles = [];
        this.beachLineY = -1;
        
        this.nodeGroup;
        this.lineGroup;
        this.arcGroup;
        this.circleGroup;

        // this.scaleEvent()
    }
    createSVG() {
        return d3.select('svg')
        .attr("width", "100%")
        .attr("height", "100%")
        .attr("class", "border-solid")
        .attr("class", "position-fixed")
        .attr("style", "transform: translate(-50%,-50%); top: 50%; left: 50%")
    }
    updateGraph() {
        this.clearGraph()
        
        this.nodeGroup = this.svg.append('g');
        for(let i = 0; i < this.n; i++) {
            this.renderPoint(this.nodes[i])
        }

        this.lineGroup = this.svg.append('g');
        for(let i = 0; i < this.lines.length; i++) {
            this.renderLines(this.lines[i]["start"], this.lines[i]["end"])
        }

        this.arcGroup = this.svg.append('g')
        for(let i = 0; i < this.arcDots.length; i++) {
            this.renderArc(this.arcDots[i])
        }

        this.circleGroup = this.svg.append('g')
        for(let i = 0; i < this.circles.length; i++) {
            this.renderCircle(this.circles[i]["center"], this.circles[i]["r"])
        }

        this.renderBeachLine(this.beachLineY, this.startX, this.startX + this.width)
        this.renderBorder(this.startX, this.startY, this.width, this.height)
    }
    clearGraph() {
        this.svg.selectAll("g").remove()
        this.svg.selectAll("path").remove()
        this.svg.selectAll("rect").remove()
        this.svg.selectAll("line").remove()
        this.svg.selectAll("circle").remove()
    }
    getNodes(data) {
        if (data == undefined) return []
        let nodes = []
        for (let i = 0; i < Object.keys(data).length; i++) {
            nodes.push({id: i, x: this.startX + data[i]["x"], y: this.startY + this.height - data[i]["y"]})
        }
        return nodes
    }
    getArcDots(data) {
        if (data == undefined) return []
        let arc = []
        for (let i = 0; i < Object.keys(data).length; i++) {
            arc.push({x: this.startX + data[i]["x"], y: this.startY + this.height - data[i]["y"]})
        }
        return arc
    }
    getLines(data) {
        if (data == undefined) return []
        let lines = []
        for (let i = 0; i < Object.keys(data).length; i++) {
            lines.push({start: {x:this.startX + data[i][0]["x"], y: this.startY + this.height - data[i][0]["y"]}, 
                        end: { x: this.startX + data[i][1]["x"], y: this.startY + this.height - data[i][1]["y"]}})
        }
        return lines
    }
    getCircles(data) {
        if (data == undefined) return []
        let circles = []
        for (let i = 0; i < Object.keys(data).length; i++) {
            circles.push({center: {x: this.startX +  data[i][0]["x"], y: this.startY + this.height - data[i][0]["y"]}, r: data[i][1]});
        }
        return circles
    }
    renderPoint(node) {
        this.nodeGroup.append("circle")
        .attr("cx", node["x"])
        .attr("cy", node["y"])
        .attr("r", 5)
        .attr("stroke", "red")
    }
    renderLines(startPoint, endPoint) {
        this.lineGroup.append("line")
        .attr("x1", startPoint["x"])
        .attr("y1", startPoint["y"])
        .attr("x2", endPoint["x"])
        .attr("y2", endPoint["y"])
        .attr("stroke", "black")
    }
    renderArc(dot) {
        this.arcGroup.append("circle")
        .attr("cx", dot["x"])
        .attr("cy", dot["y"])
        .attr("r", 1);
    }
    renderCircle(point, r) {
        console.log(point, " ", r);
        this.circleGroup.append("circle")
        .attr("cx", point["x"])
        .attr("cy", point["y"])
        .attr("r", r)
        .attr("fill","transparent")
        .attr("stroke","blue")
    }
    renderBeachLine(y, startX, endX) {
        this.svg.append("line")
        .attr("x1", startX)
        .attr("y1", y)
        .attr("x2", endX)
        .attr("y2", y)
        .attr("stroke", "black")
    }
    renderBorder(x, y, width, height) {
        this.svg.append("rect")
        .attr("x", x)
        .attr("y", y)
        .attr("width", width)
        .attr("height",height)
        .attr("fill","transparent")
        .attr("stroke", "black")
    }
    
    setData(data) {
        this.nodes = this.getNodes(data["points"])
        this.arcDots = this.getArcDots(data["arcs"])
        this.lines = this.getLines(data["lines"])
        this.circles = this.getCircles(data["circles"])
        this.beachLineY = this.startY + this.height - data["beachLine"]
        this.updateGraph()
    }

    voronoiLib() {
        let voronoi = d3.Delaunay.from(
            this.nodes,
            (d) => d.x,
            (d) => d.y
        // ).voronoi([0, 0, this.svg._groups[0][0].clientWidth, this.svg._groups[0][0].clientHeight])
        ).voronoi([this.startX-5, this.startY-5, this.startX + this.width + 5, this.startY + this.height + 5])
        this.svg.append("path")
        .attr("d",voronoi.render())
        .attr("fill", "transparent")
        .attr("stroke","black")
        
        this.svg.append("rect")
        .attr("x", this.startX - 5)
        .attr("y", this.startY - 5)
        .attr("width", this.width + 10)
        .attr("height",this.height + 10)
        .attr("fill","transparent")
        .attr("stroke", "black")

    }
    scaleEvent() {
        document.querySelector('svg').addEventListener('wheel', zoomHandler);
    }
}
function zoomHandler(event) {
    event.preventDefault();
    const svg = document.querySelector('svg')
    const scaleFactor = 0.1;
    let viewBox = svg.viewBox.animVal;
    let newWidth = viewBox.width;
    let newHeight = viewBox.height;
  
    if (event.deltaY > 0) {
      // Zoom out
      newWidth = viewBox.width * (1 + scaleFactor);
      newHeight = viewBox.height * (1 + scaleFactor);
    } else {
      // Zoom in
      newWidth = viewBox.width * (1 - scaleFactor);
      newHeight = viewBox.height * (1 - scaleFactor);
    }
  
    svg.setAttribute('viewBox', `${viewBox.x} ${viewBox.y} ${newWidth} ${newHeight}`);
  }
export default {
    Triangulations
}