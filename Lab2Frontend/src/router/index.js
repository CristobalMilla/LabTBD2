import { createRouter, createWebHistory } from "vue-router";
import Login from "@/pages/Login.vue";
import Register from "@/pages/Register.vue";
import Dashboard from "@/pages/Dashboard.vue";
import NotFound from "@/pages/NotFound.vue";
import Home from "@/pages/Home.vue";
import EmpresasDetails from "@/pages/EmpresasDetails.vue";
import EmpresaEdit from "@/pages/EmpresaEdit.vue";
import Clientes from "@/pages/Clientes.vue";
import ClienteForm from "@/pages/ClienteForm.vue";
import OpinionForm from "@/pages/OpinionForm.vue";
import Pedidos from "@/pages/Pedidos.vue";
import PedidoForm from "@/pages/PedidoForm.vue";

const routes = [
  { path: "/", redirect: "/login" },
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/home", component: Home },
  { path: "/dashboard", component: Dashboard },
  { path: "/empresasdetails", component: EmpresasDetails },
  { path: "/empresas/edit/:id", component: EmpresaEdit },
  { path: "/clientes",           component: Clientes },
  { path: "/clientes/create",    component: ClienteForm },
  { path: "/clientes/edit/:id",  component: ClienteForm },
  { path: "/opiniones/create",   component: OpinionForm },
  { path: "/opiniones/edit/:id", component: OpinionForm },
  { path: "/pedidos",            component: Pedidos },
  { path: "/pedidos/create",     component: PedidoForm },
  { path: "/:pathMatch(.*)*", component: NotFound }
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
