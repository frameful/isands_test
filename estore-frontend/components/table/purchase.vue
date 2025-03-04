<script setup lang="ts">

import { Icon } from "@iconify/vue";
import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";

interface PurchaseState {
  purchases: any[];
  page: number;
  pageSize: number;
  sort: string;
}

const purchaseState = reactive<PurchaseState>({
  purchases: [],
  page: 0,
  pageSize: 10,
  sort: "ASC",
})

function createUpdateDto(item: any) {
  return {
    'electroId': item.electroItemDto.id,
    'employeeId': item.employee.id,
    'typeId': item.purchaseType.id,
    'shopId': item.shopDto.id,
  };
}

async function fetchPurchases(page: number)  {
  purchaseState.purchases = await $fetch("http://localhost:8081/estore/api/purchase/?page=" + purchaseState.page + "&pageSize=" + purchaseState.pageSize + "&sort=" + purchaseState.sort) as any;
}

watch(() => purchaseState.page,
    (page) => {
      fetchPurchases(page);
    }
);

watch(() => purchaseState.sort,
    (sort) => {
    fetchPurchases(purchaseState.page);
    }
);


onMounted(async () => {
  await fetchPurchases(0);
  console.log(purchaseState.purchases);
})

function handleClick() {
  purchaseState.sort = purchaseState.sort === "ASC" ? "DESC" : "ASC";
}

const keyFilterSet = {
  'employeeId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число',
    name: 'Идентификатор сотрудника'
  },
  'electroId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число',
    name: 'Идентификатор товара'
  },
  'shopId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число',
    name: 'Идентификатор магазина'
  },
  'typeId': {
    regex: new RegExp("[0-9]+"),
    format: 'Число',
    name: 'Идентификатор типа покупки'
  },

}

</script>

<template>

  <Dialog>
    <DialogTrigger as-child>
      <Button variant="outline">
        Создать покупку
      </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchPurchases(purchaseState.page)" :keySet="keyFilterSet" name="purchase" endpoint="/estore/api/purchase" />
  </Dialog>


  <div>
    Страница: {{ purchaseState.page + 1 }}

    <Button @click="purchaseState.page--" variant="outline" :disabled="purchaseState.page == 0">Предыдущая страница</Button>
    -
    <Button @click="purchaseState.page++" variant="outline" :disabled="purchaseState.purchases.length !== purchaseState.pageSize">Следующая страница</Button>
  </div>

  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>employeeId</TableHead>
        <TableHead>electroItemId</TableHead>
        <TableHead>shopId</TableHead>
        <TableHead>purchaseTypeId</TableHead>
        <TableHead>
          <Button variant="ghost" @click="handleClick()">
            <p>purchaseDate</p>
            <Icon  v-if="purchaseState.sort.length > 0"
                   :icon="purchaseState.sort == 'ASC' ? 'radix-icons:arrow-up' : 'radix-icons:arrow-down'"
            />
          </Button>
        </TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="purchase in purchaseState.purchases" :key="purchase.id">
        <TableCell class="font-medium">
          {{ purchase.id }}
        </TableCell>
        <TableCell>{{ purchase.employee.id }}</TableCell>
        <TableCell>{{ purchase.electroItemDto.id }}</TableCell>
        <TableCell>{{ purchase.shopDto.id }}</TableCell>
        <TableCell>{{ purchase.purchaseType.id }}</TableCell>
        <TableCell>{{ purchase.purchaseDate ? purchase.purchaseDate : "NAH AH" }}</TableCell>
        <TableCell>
          <Dialog>
            <DialogTrigger>
              <Button variant="outline">
                <Icon icon="radix-icons:pencil-1" />
              </Button>
            </DialogTrigger>
            <NewEntryDialogue :itemToEdit="createUpdateDto(purchase)"
                              :editing="true"
                              @newEntry="fetchPurchases(purchaseState.page)"
                              :keySet="keyFilterSet"
                              name="purchase" :endpoint="'/estore/api/purchase/' + purchase.id" />
          </Dialog>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>

<style scoped>

</style>