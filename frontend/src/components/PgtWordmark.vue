<script setup>
defineProps({ compact: Boolean, inverse: Boolean })
</script>

<template>
  <div class="wordmark" :class="{ 'wordmark--compact': compact, 'wordmark--inverse': inverse }">
    <div class="wordmark__seal" aria-hidden="true">
      <span>U</span>
      <i></i>
    </div>
    <div class="wordmark__copy">
      <strong>PGT</strong>
      <span>USACH</span>
    </div>
  </div>
</template>

