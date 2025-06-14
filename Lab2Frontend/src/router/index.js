import { createRouter, createWebHistory } from "vue-router";
import Login from "@/pages/Login.vue";
import Register from "@/pages/Register.vue";
import Dashboard from "@/pages/Dashboard.vue";
import NotFound from "@/pages/NotFound.vue";
import Home from "@/pages/Home.vue";
import TaskDetails from "@/pages/TaskDetails.vue";

// const routes = [
//   { path: "/", component: { template: "<div>¡Hola Mundo!</div>" } },
//   { path: "/test", component: { template: "<div>Página de Prueba</div>" } },
// ];

const routes = [
  { path: "/", redirect: "/login" },
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/home", component: Home }, 
  { path: "/dashboard", component: Dashboard },
  { path: "/:pathMatch(.*)*", component: NotFound },
  { path: "/taskdetails", component: TaskDetails },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Redirección si no hay token
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("jwtToken");
  if (to.meta.requiresAuth && !isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
