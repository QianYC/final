import math
import numpy as np
import matplotlib.pyplot as plt

tasks_str = ["All Read", "All Write", "Most Read", "Equal R&W", "Most Write"]
impls_str = ["Std", "Coarse", "Fine", "RW", "CAS", "Ticket"]

tasks_count = 5
impls_count = 6


def draw_perf(input_data, task_id):
    base = [1, 2, 4, 8, 16]

    for i in range(impls_count):
        plt.plot(base, input_data[i], label=impls_str[i])
    # plotting the line 2 points
    plt.xlabel('thread num')
    # Set the y axis label of the current axis.
    plt.ylabel('relative perf')
    plt.title("%s task stat.png" % (tasks_str[task_id]))
    # show a legend on the plot
    plt.legend()

    plt.savefig("./%s task stat.png" % (tasks_str[task_id]))
    # Display a figure.
    plt.show()


if __name__ == "__main__":
    """
    # ensure a total of 30 lines for inputs.
    # Every 5 lines belong to an implementation
    # There are a total of 6 impls
    # Each columns represents different thread nums, 1, 2, 4, 8, 16
    # The impls should be arranged in the following order ["Std", "Coarse", "Fine", "RW", "CAS", "Ticket"]
    """
    in_file = "./jmh_data"
    input = np.genfromtxt(in_file, delimiter=' ', dtype=np.float64, encoding='UTF-8')

    for task_id in range(tasks_count):
        index = [task_id + impl * tasks_count for impl in range(6)]
        draw_perf(input[index].copy(), task_id)

