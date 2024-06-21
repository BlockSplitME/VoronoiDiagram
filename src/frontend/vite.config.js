import { fileURLToPath, URL } from 'node:url'
import envCompatible from 'vite-plugin-env-compatible';

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    vue(),
    envCompatible(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  
  server: {
    port: Number(process.env.FRONT_PORT ?? 3000),
    proxy: {
        '/api': {
            target: `http://localhost:${Number(process.env.BACK_PORT ?? 8083)}`,
            // rewrite: (path) => path.replace(/^\/api/, ''),
            changeOrigin: true
        }
    }, 
  }
  
})
