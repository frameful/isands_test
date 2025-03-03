<script setup lang="ts">

import { Icon } from "@iconify/vue";
import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";

const sort = ref("");
const purchases = ref([]);

async function fetchPurchase(sort: string)  {
  purchases.value = await $fetch(`http://localhost:8081/estore/api/purchase${sort.length > 0 ? "?sort=" + sort : ""}`) as any;
}

function handleClick() {
  sort.value = sort.value.length > 0 ? sort.value === "ASC" ? "DESC" : "ASC" : "ASC";
  fetchPurchase(sort.value);
}

const keyFilterSet = {
  'electroId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число'
  },
  'employeeId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число'
  },
  'typeId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число'
  },
  'shopId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число'
  },
}

onMounted(async () => {
  fetchPurchase("");
})

</script>

<template>

  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать покупку
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchPurchase(sort)" :keySet="keyFilterSet" name="purchase" endpoint="/estore/api/purchase" />
  </Dialog>

  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>employeeId</TableHead>
        <TableHead>electroItemId</TableHead>
        <TableHead>shopId</TableHead>
        <TableHead>
          <Button variant="ghost" @click="handleClick()">
            <p>purchaseDate</p>
            <Icon  v-if="sort.length > 0" :icon="sort == 'ASC' ? 'radix-icons:arrow-up' : 'radix-icons:arrow-down'"></Icon>
          </Button>
        </TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="purchase in purchases" :key="purchase.id">
        <TableCell class="font-medium">
          {{ purchase.id }}
        </TableCell>
        <TableCell>{{ purchase.employee.id }}</TableCell>
        <TableCell>{{ purchase.electroItemDto.id }}</TableCell>
        <TableCell>{{ purchase.shopDto.id }}</TableCell>
        <TableCell>{{ purchase.purchaseDate ? purchase.purchaseDate : "NAH AH" }}</TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>

<style scoped>

</style>