<template>
    <div id="svgBlock" class="position-fixed w-100 h-100">
        <svg></svg>
    </div>
    <Panel @updateParent="receiveEmit"/>
</template>

<script>
import * as d3 from '../../node_modules/d3';
import graph from '../js/triangulations'

import Panel from '../components/Panel.vue'

export default {
    data: () => ({
        graph: null,
    }),
    methods: {
        createField(data) {
            if(this.graph) this.graph.clearGraph()
            if(data.length != 0) this.graph = new graph.Triangulations(data, d3)
        },
        receiveEmit(data) {
            this.createField(data)
        }
    },
    mounted() {

    },
    components: {
        Panel
    }
}
</script>