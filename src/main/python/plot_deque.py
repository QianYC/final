import math
import numpy as np
import matplotlib.pyplot as plt

tasks_str = ["All Write", "All Read", "Most Read", "Equal R&W", "Most Write"]
impls_str = ["Std", "Coarse", "Fine", "RW", "CAS", "Ticket"]

tasks_count = 5
impls_count = 6


def draw_perf(input_data, task_id):
    print(input_data)
    base = [1, 2, 4, 8, 16]

    for i in range(impls_count):
        plt.plot(base, input_data[i], label=impls_str[i])
    # plotting the line 2 points
    plt.xlabel('thread num')
    # Set the y axis label of the current axis.
    plt.ylabel('throughput ops/ms')
    plt.yscale('log')
    plt.title("%s task stat.png" % (tasks_str[task_id]))
    # show a legend on the plot
    plt.legend()

    plt.savefig("./%s task stat.png" % (tasks_str[task_id]))
    # Display a figure.
    plt.show()


if __name__ == "__main__":
    """
    # ensure a total of 30 lines for inputs.
    # Every 6 lines belong to a task
    # Each task has 6task_id implementations
    # There are a total of 5task_id tasks
    # Each columns represents different thread nums, 1, 2, 4, 8, 16
    # The impls should be arranged in the following order ["Std", "Coarse", "Fine", "RW", "CAS", "Ticket"]
    """
    in_file = "./jmh_data"
    input = np.genfromtxt(in_file, delimiter=' ', dtype=np.float64, encoding='UTF-8')

    for task_id in range(tasks_count):
        index = [x for x in range(task_id * 6, task_id * 6 + 6)]
        print(index)
        draw_perf(input[index].copy(), task_id)

