<script setup lang="ts">
import { Button } from "@/components/ui/button";
import {
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "~/components/ui/toast";
import axios from "axios";

const props = defineProps<{
  keySet: filterSet;
  name: string;
  endpoint: string;
  editing?: boolean;
  itemToEdit?: any;
}>();

const emit = defineEmits(["newEntry"]);

interface filterSet {
  [key: string]: {
    regex: RegExp;
    format: string;
    name: string;
  };
}

interface InputState {
  [key: string]: {
    value: string;
    valid: boolean;
  };
  [Symbol.iterator]: () => Generator<
    {
      [key: string]: {
        value: string;
        valid: boolean;
      };
    },
    void,
    unknown
  >;
}

onMounted(() => {
  console.log("item", props.itemToEdit);
      if (!props.editing || props.itemToEdit == null) return;
      Object.keys(props.keySet).forEach((key: string) => {
        console.log("key", key, "value", props.itemToEdit);
        inputState[key].value = props.itemToEdit[key];
      });
    }
);

const inputState = reactive<InputState>({
  [Symbol.iterator]: function* () {
    for (const key of Object.keys(this)) {
      yield { [key]: this[key] };
    }
  },
});

for (const key of Object.keys(props.keySet)) {
  inputState[key] = {
    value: "",
    valid: false,
  };
}

watchEffect(() => {
  Object.keys(props.keySet).forEach((key) => {
    inputState[key].valid = props.keySet[key].regex.test(inputState[key].value);
  });
});

async function handleClick() {
  if (
    Object.keys(props.keySet).some((key) => {
      return !inputState[key].valid;
    })
  ) {
    toast({
      title: "Ошибка",
      description: "Данные введены неправильно",
    });
    return;
  }

  const body: {
    [key: string]: any;
  } = {};
  Object.keys(props.keySet).forEach((key) => {
    body[key] = inputState[key].value;
  });
  try {
    console.log(props.endpoint);
    const response = props.editing ? await axios.patch(
      "http://localhost:8081" + props.endpoint,
      body
    ) : await axios.post(
        "http://localhost:8081" + props.endpoint,
        body
    );
    toast({
      title: props.editing ? "Предмет обновлен" : "Предмет добавлен",
      description: h('pre', { class: 'mt-2 w-[340px] rounded-md bg-slate-950 p-4' }, h('code', { class: 'text-white' }, JSON.stringify(body, null, 2))),
    });
    emit("newEntry");
  } catch (e: any) {
    toast({
      title: "Ошибка",
      description: e.message,
    });
  }
}

const { toast } = useToast();
</script>

<template>
  <DialogContent class="sm:max-w-[500px]">
    <DialogHeader>
      <DialogTitle>{{ props.editing ? "Редактирование" : "Создание" }} {{ props.name }}</DialogTitle>
      <DialogDescription>
        Введите данные в правильном формате.
      </DialogDescription>
    </DialogHeader>
    <div class="grid gap-4 py-4">
      <div
        class="grid grid-cols-4 items-center gap-4 relative"
        v-for="key in Object.keys(props.keySet)"
      >
        <Label
          :for="key"
          :class="{
            'text-right': true,
            [inputState[key].valid ? 'text-green-500' : 'text-red-500']: true,
          }"
        >
          {{ keySet[key].name }}
        </Label>
        <Input
          id="name"
          :placeholder="props.keySet[key].format"
          class="col-span-3 before:content-[Hello]"
          v-model="inputState[key].value"
        />
      </div>
    </div>
    <DialogFooter>
      <Button @click="handleClick"> {{ editing ? "Обновить" : "Создать" }} </Button>
    </DialogFooter>
  </DialogContent>
</template>
