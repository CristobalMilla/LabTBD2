<template>
  <button :class="'btn ' + variant" v-bind="$attrs" v-on="$listeners">
    <slot></slot>
  </button>
</template>

<script>
export default {
  props: {
    variant: {
      type: String,
      default: "primary",
    },
  },
};
</script>

<style>
.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
}
.btn.primary {
  background-color: #007bff;
  color: white;
}
.btn.secondary {
  background-color: #6c757d;
  color: white;
}
</style>
