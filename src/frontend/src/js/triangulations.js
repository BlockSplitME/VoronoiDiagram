let d3 = null
class Triangulations {
    constructor(data, lib_d3) {
        d3 = lib_d3
        this.width = data[0]
        this.height = data[1]
        this.n = data[2]
        
        this.svg = this.createSVG()

        this.startX = this.svg._groups[0][0].clientWidth / 2 - this.width / 2
        this.startY = this.svg._groups[0][0].clientHeight / 2 - this.height / 2

        this.nodes = this.getNodes(this.n, this.width, this.height)
        
        this.nodeElements
        
        this.nodeGroup = this.svg.append('g')
        .attr('class', 'nodes')
        
        this.voronoiLib()

        for(let i = 0; i < this.n; i++) {
            this.renderPoint(this.nodes[i])
        }
        
        this.scaleEvent()
    }
    createSVG() {
        return d3.select('svg')
        .attr("width", "100%")
        .attr("height", "100%")
        .attr("class", "border-solid")
        .attr("class", "position-fixed")
        .attr("style", "transform: translate(-50%,-50%); top: 50%; left: 50%")
        .attr("viewBox", "0 0 100 100")
    }
    getNodes(n, W, H) {
        let nodes = []
        for (let i = 0; i < n; i++) {
            nodes.push({id: i, x: this.startX + getRandomInt(W), y: this.startY + getRandomInt(H)})
        }
        return nodes
    }
    renderPoint(node) {
        this.nodeGroup.append("circle")
        .attr("cx", node["x"])
        .attr("cy", node["y"])
        .attr("r", 5);
    }
    clearGraph() {
        this.nodes = []
        this.svg.select("g").remove()
        this.svg.select("path").remove()
        this.svg.select("rect").remove()
        this.updateSimulation()
    }
    updateGraph() {
        
    }
    updateSimulation() {
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
function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}
export default {
    Triangulations
}