<template>
    <v-switch class="position-fixed ma-3"  color="red"
        hide-details label="Parametrs" v-model="drawer"></v-switch>
        <Transition name="slide-fade">
          <v-card id="menuField" v-show="drawer" variant="outlined" class="position-fixed ma-5 mt-15 text-h6 w-50" min-width="200" max-width="400">
            <v-sheet class="w-100 mx-auto">
                <v-form fast-fail @submit.prevent>
                    <v-text-field
                        v-model="parameters.param_W"
                        label="W"
                        :rules="paramRules"
                    ></v-text-field>
                    
                    <v-text-field
                        v-model="parameters.param_H"
                        label="H"
                        :rules="paramRules"
                    ></v-text-field>

                    <v-text-field
                        v-model="parameters.param_n"
                        label="n"
                        :rules="paramRules"
                    ></v-text-field>

                    <v-btn @click="createField" block class="mt-2" variant="outlined">Запуск</v-btn>
                    <v-btn @click="doStep" block class="mt-2" variant="outlined">Сделать шаг</v-btn>
                    <v-btn @click="getResult" block class="mt-2" variant="outlined">Результат</v-btn>
                </v-form>
            </v-sheet>
          </v-card>
        </Transition>
</template>

<script>
  export default {
    emits: ['createField', 'doStep', 'getResult'],
    data: () => ({
        parameters: {
          param_n: 100,
          param_W: 1000,
          param_H: 800
        },  
        paramRules: [
            value => {
            if (value > 0) return true

            return 'Incorrect parameter.'
            },
        ],
        drawer: true,
    }),
    methods: {
      createField() { 
        let params = []
        for(let i in this.parameters) {
          let param = this.parameters[i]
          if(!(/^\d+$/.test(param)) || param < 1) {
            return false
          }
          params.push(+param)
        }
        this.$emit('createField', params)
      },
      doStep() {
        this.$emit('doStep')
      },
      getResult() {
        this.$emit('getResult')
      }
    },
  }
</script>

<style type="text/css">
  .slide-fade-enter-active {
  transition: all 0.3s ease-out;
  }
  
  .slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(0.4, 0, 1, 1);
  }
  
  .slide-fade-enter-from,
  .slide-fade-leave-to {
  transform: translateX(-20px);
  opacity: 0;
  }
  </style>