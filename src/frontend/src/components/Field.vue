<template>
    <div id="svgBlock" class="position-fixed w-100 h-100">
        <svg></svg>
    </div>
    <Panel @createField="createField" @doStep="doStep" @getResult="getResult"/>
</template>

<script>
import * as d3 from '../../node_modules/d3';
import graph from '../js/triangulations'
import requests from '../js/requests';

import Panel from '../components/Panel.vue'

export default {
    data: () => ({
        graph: null,
    }),
    methods: {
        async createField(data) {
            let result = await requests.initField(data[0], data[1], data[2])
            if(this.graph) this.graph.clearGraph()
            if(data.length != 0) this.graph = new graph.Triangulations(data, d3)
            console.log("Field init.");
            this.updateGraph(result)
        },
        async doStep() {
            let result = await requests.doStep()
            this.updateGraph(result)
        },
        async getResult() {
            let result = await requests.getResult()
            this.updateGraph(result)
        },
        updateGraph(result) {
            if(this.graph) {this.graph.setData(result); console.log("Graph update.");}
            else alert("Init graph!")
        }
    },
    mounted() {

    },
    components: {
        Panel
    }
}
</script>